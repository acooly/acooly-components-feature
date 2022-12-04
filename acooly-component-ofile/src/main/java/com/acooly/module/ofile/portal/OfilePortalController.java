/**
 * create by zhangpu date:2015年5月16日
 */
package com.acooly.module.ofile.portal;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.FileOperateErrorCodes;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.*;
import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.ObsService;
import com.acooly.module.obs.common.OssFile;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.auth.OFileUploadAuthenticate;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.enums.AccessTypeEnum;
import com.acooly.module.ofile.enums.OFileType;
import com.acooly.module.ofile.enums.StorageTypeEnum;
import com.acooly.module.ofile.service.OnlineFileService;
import com.acooly.module.ofile.support.OfileSupportService;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.utils.ShiroUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.ObjectMetadata;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author zhangpu
 */
@Slf4j
@Controller
@RequestMapping("/ofile")
public class OfilePortalController
        extends AbstractJsonEntityController<OnlineFile, OnlineFileService> {
    public static final String WATERMARK_TEXT = "watermarkText";
    public static final String WATERMARK_IMAGE = "watermarkImage";
    /**
     * 单次上传时，动态指定的存储子路径（相对storageRoot的子路径）参数，请求方可动态传入，默认为空
     */
    public static final String STORAGE_SUB_PATH = "storageSubPath";
    private static final Pattern ABSOLUTE_URL = Pattern.compile("\\A[a-z0-9.+-]+://.*", 2);
    @Resource
    private OnlineFileService onlineFileService;
    @Autowired
    private OFileProperties oFileProperties;

    @Resource(name = "ofileUploadAuthenticateSpringProxy")
    private OFileUploadAuthenticate ofileUploadAuthenticate;

    @Autowired
    private OfileSupportService ofileSupportService;

    @Autowired
    private ObsProperties obsProperties;
    @Autowired
    private ObsService obsService;

    private static boolean isAbsoluteUrl(String url) {
        return ABSOLUTE_URL.matcher(url).matches();
    }

    @RequestMapping("upload")
    @ResponseBody
    public JsonListResult<OnlineFile> upload(
            HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<OnlineFile> result = new JsonListResult<OnlineFile>();
        try {
            ofileUploadAuthenticate.authenticate(request);
            // 上传文件格式
            getUploadConfig().setAllowExtentions(oFileProperties.getAllowExtentions());
            getUploadConfig().setMaxSize(oFileProperties.getMaxSize());
            getUploadConfig().setStorageRoot(getStoragePath(request));
            getUploadConfig().setUseMemery(false);
            getUploadConfig().setStorageNameSpace(oFileProperties.getStorageNameSpace());
            String storageType = ofileSupportService.getStorageType(request);
            List<OnlineFile> onlineFiles = Lists.newArrayList();
            if (StorageTypeEnum.OBS.getCode().equals(storageType)) {
                //obs上传
                uploadByObs(request, onlineFiles);
            } else {
                //本地上传
                Map<String, UploadResult> uploadResults = doUpload(request);
                UploadResult uploadResult = null;
                for (Map.Entry<String, UploadResult> entry : uploadResults.entrySet()) {
                    uploadResult = entry.getValue();
                    if (uploadResult == null || uploadResult.getSize() <= 0) {
                        continue;
                    }
                    onlineFiles.add(fillOnlineFile(request, uploadResult));
                }
                if (onlineFiles.isEmpty()) {
                    throw new RuntimeException("请求中没有可上传的文件");
                }
            }
            onlineFileService.saves(onlineFiles);
            result.setRows(onlineFiles);
            result.setTotal((long) onlineFiles.size());
            result.appendData("serverRoot", oFileProperties.getServerRoot());
            log.info("ofile文件上传成功。files:{}", onlineFiles.toString());
        } catch (Exception e) {
            handleException(result, "上传文件", e);
        }
        return result;
    }

    private void uploadByObs(HttpServletRequest request, List<OnlineFile> onlineFiles) throws IOException {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> multiValueMap = multiRequest.getMultiFileMap();
        if (multiValueMap.size() <= 0) {
            throw new RuntimeException("请求中没有可上传的文件");
        }
        // 通用参数配置，可由业务系统重写
        String metadata = ofileSupportService.getMetadata(request);
        String bucketName = ofileSupportService.getBucketName(request);
        String userName = getSessionUser(request);
        Iterator<String> iterator = multiValueMap.keySet().iterator();
        while (iterator.hasNext()) {
            String inputName = iterator.next();
            List<MultipartFile> multipartFileList = multiValueMap.get(inputName);
            for (MultipartFile multipartFile : multipartFileList) {
                doObsUpload(onlineFiles, multipartFile, metadata, inputName, userName, bucketName, request);
            }
        }
    }

    protected void doObsUpload(List<OnlineFile> onlineFileList, MultipartFile multipartFile, String metadata, String inputName, String userName, String bucketName, HttpServletRequest request) throws IOException {
        if (multipartFile == null || multipartFile.getSize() <= 0) {
            return;
        }
        UploadConfig uploadConfig = getUploadConfig();
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = Files.getFileExtension(originalFilename);
        OFileType oFileType = OFileType.with(extension);
        if (multipartFile.getSize() > uploadConfig.getMaxSize()) {
            throw new BusinessException(
                    "文件[" + originalFilename + "]大小操作限制，最大限制:" + uploadConfig.getMaxSize() / 1024 / 1024 + "M");
        }
        if (!StringUtils.containsIgnoreCase(uploadConfig.getAllowExtentions(), extension)) {
            throw new BusinessException(
                    "文件[" + originalFilename + "]类型不支持，支持类型:" + uploadConfig.getAllowExtentions());
        }
        // obs key生成策略，可由业务系统重写
        String key = ofileSupportService.generateKey(request, inputName, originalFilename);

        log.info("inputName={},OriginalFilename={},fileSize={},oFileType={}", inputName, originalFilename, multipartFile.getSize(), oFileType.getCode());
        ObjectMetadata objectMetadata = null;
        if (Strings.isNotBlank(metadata)) {
            objectMetadata = new ObjectMetadata();
            objectMetadata.setUserMetadata(JSON.parseObject(userName, Map.class));
        }
        OssFile ossFile = obsService.putObject(bucketName, key, multipartFile.getInputStream(), objectMetadata);
        String accessUrl = ossFile.getUrl();
        //公网可访问的缩略图地址，按ofile配置进行缩放
        String accessThumbnailUrl = getAccessThumbnailUrl(accessUrl, request);
        AccessTypeEnum accessType = AccessTypeEnum.OBS_PUBLIC;

        // 采用sts令牌形式转换可访问路径
        if (ObsProperties.Provider.Aliyun.equals(obsProperties.getProvider()) && obsProperties.getAliyun().isStsEnable()) {
            accessUrl = obsService.getAccessUrlBySts(bucketName, ossFile.getKey(), null, ofileSupportService.getExpireDate());
            accessThumbnailUrl = obsService.getAccessUrlBySts(bucketName, ossFile.getKey(), ofileSupportService.getProcess(oFileType), ofileSupportService.getExpireDate());
            accessType = AccessTypeEnum.OBS_STS;
        }
        OnlineFile onlineFile = new OnlineFile();
        onlineFile.setObjectId(ossFile.getETag());
        onlineFile.setInputName(inputName);
        onlineFile.setFileName(Files.getNameWithoutExtension(ossFile.getKey()) + "." + extension);
        onlineFile.setFileType(oFileType);
        onlineFile.setModule(ofileSupportService.getModule(request));
        onlineFile.setFileExt(extension);
        onlineFile.setFileSize(multipartFile.getSize());
        onlineFile.setFilePath(key);
        onlineFile.setThumbnail(key);
        onlineFile.setUserName(userName);
        onlineFile.setOriginalName(originalFilename);
        onlineFile.setMetadatas(metadata);
        onlineFile.setAccessType(accessType);
        onlineFile.setBucketName(bucketName);
        onlineFile.setAccessUrl(accessUrl);
        onlineFile.setAccessThumbnailUrl(accessThumbnailUrl);
        onlineFileList.add(onlineFile);
    }

    protected String getAccessThumbnailUrl(String accessUrl, HttpServletRequest request) {
        return accessUrl.concat("?x-oss-process=image/resize,w_").concat(String.valueOf(ofileSupportService.getThumbnailSize(request)))
                .concat(",").concat("h_").concat(String.valueOf(ofileSupportService.getThumbnailSize(request)));
    }

    @RequestMapping("kindEditor")
    @ResponseBody
    public Map<String, Object> kindEditor(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            JsonListResult<OnlineFile> onlineFiles = upload(request, response);
            result.put("error", 0);
            result.put("url", Collections3.getFirst(onlineFiles.getRows()).getAccessUrl());
        } catch (Exception e) {
            result.put("error", 1);
            result.put("message", "文件上传失败:" + e.getMessage());
        }
        return result;
    }

    /**
     * 访问文件
     */
    @RequestMapping("download/{id}")
    @ResponseBody
    public Object download(
            @PathVariable String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        OnlineFile onlineFile = getEntityService().get(Long.valueOf(id));
        return doDownload(
                request,
                response,
                onlineFile.getOriginalName(),
                onlineFile.getFilePath(),
                onlineFile.getFileType(), onlineFile.getAccessType(), onlineFile.getBucketName(), null);
    }

    /**
     * 访问图片
     */
    @RequestMapping("image/{id}")
    @ResponseBody
    public Object image(
            @PathVariable String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        OnlineFile onlineFile = getEntityService().get(Long.valueOf(id));
        return doDownload(
                request,
                response,
                onlineFile.getOriginalName(),
                onlineFile.getFilePath(),
                onlineFile.getFileType(), onlineFile.getAccessType(), onlineFile.getBucketName(), null);
    }

    /**
     * 访问缩略图
     */
    @RequestMapping("thumb/{id}")
    @ResponseBody
    public Object thumbnail(
            @PathVariable String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        OnlineFile onlineFile = getEntityService().get(Long.valueOf(id));
        return doDownload(
                request,
                response,
                onlineFile.getOriginalName(),
                onlineFile.getThumbnail(),
                onlineFile.getFileType(), onlineFile.getAccessType(), onlineFile.getBucketName(), ofileSupportService.getProcess(onlineFile.getFileType()));
    }

    /**
     * 通用下载（根据传入URL）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("download")
    @ResponseBody
    public Object download(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        obsService.getObject(null, "test/20200528/960069987391651840.jpg", null);
        return null;
//        String path = request.getParameter("path");
//        if (isAbsoluteUrl(path)) {
//            path = new URL(path).getPath();
//        }
//
//        checkFilePathAttacks(path);
//
//        @SuppressWarnings("deprecation")
//        // 兼容servlet2.4环境
//                String filePath = request.getRealPath(path);
//        OnlineFile onlineFile = onlineFileService.findByFilePathAndBucket(filePath, null);
//        if (onlineFile == null) {
//            throw new RuntimeException("文件不存在");
//        }
//        if (onlineFile.getAccessType() == null || AccessTypeEnum.LOCAL_STORAGE.equals(onlineFile.getAccessType())) {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                file = new File(oFileProperties.getStorageRoot() + path);
//            }
//            if (!file.exists()) {
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                return null;
//            }
//        }
//        return doDownload(
//                request,
//                response,
//                onlineFile.getOriginalName(),
//                onlineFile.getFilePath(),
//                onlineFile.getFileType(), onlineFile.getAccessType(), onlineFile.getBucketName(), null);
    }

    private void checkFilePathAttacks(String absolutePath) {

        File file = new File(absolutePath);
        if (!file.isAbsolute()) {
            throw new BusinessException("请输入文件绝对路径");
        }
        if (file.isDirectory()) {
            throw new BusinessException("不支持文件夹下载");
        }

        String canonicalPath;
        String usedAbsolutePath;
        try {
            canonicalPath = file.getCanonicalPath();
            usedAbsolutePath = file.getAbsolutePath();
        } catch (IOException e) {
            throw new BusinessException("get Canonical path failed", e);
        }
        // 防御路径穿越
        // attacks, e.g. "1/../2/"
        if (!canonicalPath.equals(usedAbsolutePath)) {
            throw new BusinessException("不能输入上级文件夹目录");
        }
    }


    protected Object doDownload(
            HttpServletRequest request,
            HttpServletResponse response,
            String fileName,
            String filePath,
            OFileType fileType, AccessTypeEnum accessType, String bucketName, String processStyle) {
        OutputStream out = null;
        InputStream in = null;
        try {
            checkReferer(request);
            doHeader(fileName, response, fileType);
            if (accessType == null || AccessTypeEnum.LOCAL_STORAGE.equals(accessType)) {
                File file = new File(getStorageRoot() + filePath);
                in = FileUtils.openInputStream(file);
            } else {
                OssFile ossFile = obsService.getObject(bucketName, filePath, processStyle);
                in = ossFile.getFileInputStream();
            }
            out = response.getOutputStream();
            IOUtils.copyLarge(in, out);
            out.flush();
            return null;
        } catch (Exception e) {
            JsonResult result = new JsonResult();
            handleException(result, "访问文件", e);
            return result;
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    protected OnlineFile fillOnlineFile(HttpServletRequest request, UploadResult uploadResult) {
        OnlineFile onlineFile = new OnlineFile();
        File file = uploadResult.getFile();
        onlineFile.setInputName(uploadResult.getParameterName());
        onlineFile.setFileExt(FilenameUtils.getExtension(file.getName()));
        onlineFile.setFileName(file.getName());
        onlineFile.setFilePath(getFilePath(request, file));
        onlineFile.setFileSize(file.length());
        onlineFile.setFileType(OFileType.with(onlineFile.getFileExt()));
        onlineFile.setModule(ofileSupportService.getModule(request));
        onlineFile.setMetadatas(ofileSupportService.getMetadata(request));
        onlineFile.setOriginalName(uploadResult.getName());
        onlineFile.setObjectId(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
        onlineFile.setUserName(getSessionUser(request));
        if (onlineFile.getFileType() == OFileType.picture) {
            String thumbnailFilePath;
            File thumbnailFile =
                    new File(
                            file.getParent(),
                            FilenameUtils.getBaseName(file.getName())
                                    + "_thum."
                                    + FilenameUtils.getExtension(file.getName()));

            int thumbSize = ofileSupportService.getThumbnailSize(request);
            try {
                Images.resize(file.getPath(), thumbnailFile.getPath(), thumbSize, thumbSize, false);
                thumbnailFilePath = thumbnailFile.getPath();
            } catch (Exception e) {
                log.error("缩略图生成失败,使用原图地址", e);
                thumbnailFilePath = file.getPath();
            }
            onlineFile.setThumbnail(getFilePath(thumbnailFilePath));
        }

        if (onlineFile.getFileType() == OFileType.picture) {
            //水印图片
            if (oFileProperties.getWatermarkimage().isEnable()) {
                String watermarkImage = request.getParameter(WATERMARK_IMAGE);
                if (StringUtils.isNotEmpty(watermarkImage) && watermarkImage.equals("true")) {
                    addWaterMarkImage(onlineFile);
                    log.info("添加水印图片成功，路径：{}", onlineFile.getAbsolutePath());
                }
            }
            //水印文字
            if (oFileProperties.getWatermarktext().isEnable()) {
                try {
                    String watermarkText = request.getParameter(WATERMARK_TEXT);
                    if (StringUtils.isNotEmpty(watermarkText) && watermarkText.equals("true")) {
                        addWaterMarkText(onlineFile);
                        log.info("添加水印文字成功，路径：{}", onlineFile.getAbsolutePath());
                    }
                } catch (Exception e) {
                    log.error("添加水印文字失败，文件名：{},路径：{}", onlineFile.getFileName(), onlineFile.getAbsolutePath(), e);
                }
            }
        }
        return onlineFile;
    }

    protected void addWaterMarkImage(OnlineFile onlineFile) {
        String originfilePath = onlineFile.getAbsolutePath();

        OFileProperties.Watermarkimage watermarkImage = oFileProperties.getWatermarkimage();
        String markImageFilePath = watermarkImage.getMarkImageFilePath();
        Assert.notNull(markImageFilePath, "水印图片路径不能为空");
        File imageFile = new File(markImageFilePath);
        if (!imageFile.exists()) {
            throw new AppConfigException("水印图片不存在，路径：" + markImageFilePath);
        }
        try {
            Images.pressImageFile(new FileInputStream(new File(markImageFilePath)), new File(originfilePath), watermarkImage.getX(), watermarkImage.getY());
        } catch (FileNotFoundException e) {
            log.error("添加水印图片失败，文件名：{},路径：{}", onlineFile.getFileName(), originfilePath, e);
        }
    }

    protected void addWaterMarkText(OnlineFile onlineFile) {
        OFileProperties.Watermarktext watermarkText = oFileProperties.getWatermarktext();
        Assert.notNull(watermarkText.getMarkText(), "水印图片文字不能为空");
        Images.pressText(watermarkText.getMarkText(), onlineFile.getAbsolutePath(), null, Font.ITALIC, Color.BLACK, watermarkText.getFontSize(), watermarkText.getX(), watermarkText.getY(), watermarkText.getAlpha(), null);
    }

    protected void doHeader(String fileName, HttpServletResponse response, OFileType ofileType) {
        if (ofileType == OFileType.picture) {
            response.setContentType("image/jpeg");
        } else if (ofileType == OFileType.app) {
            response.setContentType("application/vnd.android.package-archive");
        } else {
            response.setContentType("application/octet-stream");
        }
        response.setHeader("Content-Disposition", "attachment");
        response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
    }

    protected String getSessionUser(HttpServletRequest request) {
        String userName;
        userName = request.getParameter("userName");
        if (Strings.isNotBlank(userName)) {
            return userName;
        }
        String sessionKeys = oFileProperties.getCheckSessionKey();
        if (Strings.isBlank(sessionKeys)) {
            return null;
        }

        Object user;
        for (String key : sessionKeys.split(",")) {
            user = request.getSession().getAttribute(key);
            if (user != null) {
                userName = user.toString();
            }
        }
        if (userName != null && userName.length() > 31) {
            userName = userName.substring(0, 28) + "...";
        }
        return userName;
    }

    protected String getFilePath(HttpServletRequest request, File file) {
        String filePath = file.getPath();
        return getFilePath(filePath);
    }

    protected String getFilePath(String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");
        return "/" + StringUtils.substringAfter(filePath, getStorageRoot());
    }

    protected String getStorageRoot() {
        return oFileProperties.getStorageRoot().replaceAll("\\\\", "/");
    }

    protected void checkReferer(HttpServletRequest request) {
        if (!oFileProperties.isCheckReferer()) {
            return;
        }
        try {
            String referer = request.getHeader(HttpHeaders.REFERER);
            if (Strings.isNotBlank(referer)) {
                String refererHost = new URL(referer).getHost();
                String serverHost = new URL(request.getRequestURL().toString()).getHost();
                if (!Strings.equals(refererHost, serverHost)) {
                    throw new RuntimeException("非法访问");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取本次上传存储物理路径
     *
     * @param request
     */
    protected String getStoragePath(HttpServletRequest request) {
        String storageSubPath = Servlets.getParameter(STORAGE_SUB_PATH);
        if (Strings.isNotBlank(storageSubPath)) {
            storageSubPath = storageSubPath.replaceAll("\\\\", "/");
            storageSubPath = Paths.get(getStorageRoot(), storageSubPath).toString();
            try {
                FileUtils.forceMkdir(new File(storageSubPath));
            } catch (Exception e) {
                log.warn("创建上传存储文件夹失败。路径：{},原因:{)", storageSubPath, e.getMessage());
            }
            return storageSubPath;
        }
        return getStorageRoot();
    }

    /**
     * 重写Transfer方法，用于对上传的图片进行压缩 liin 2020-08-24
     *
     * @param mfile
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    protected File doTransfer(MultipartFile mfile, HttpServletRequest request) {
        try {
            return doFileTransfer(mfile, request);
        } catch (Exception e) {
            log.error("文件上传 失败: {}。from:{}", FileOperateErrorCodes.FILE_UPLOAD_TRANSFER_ERROR, mfile.getName());
            throw new BusinessException(FileOperateErrorCodes.FILE_UPLOAD_TRANSFER_ERROR);
        }
    }

    protected File doFileTransfer(MultipartFile mfile, HttpServletRequest request) throws Exception {
        // 转存到服务器，返回服务器文件
        File destFile = new File(getUploadFileName(mfile.getOriginalFilename(), uploadConfig, request));
        // modify by zhangpu on 20141026
        // for：移动文件前，检查目标文件所在文件夹是否存在，不存在在创建. update to version: 2.2.4
        File pathFile = destFile.getParentFile();
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        //非图片文件，或者未开启图片压缩功能 不进行后续处理
        if (!Images.isImage(mfile.getOriginalFilename()) || !oFileProperties.getResizePicture().isEnable()) {
            mfile.transferTo(destFile);
            return destFile;
        }

        //读取原始图片的宽和高
        BufferedImage bufferedImage = ImageIO.read(mfile.getInputStream());

        // oversize = false 对图片进行放大或缩小操作
        if (!oFileProperties.getResizePicture().isOversize()) {
            Thumbnails.of(bufferedImage).size(oFileProperties.getResizePicture().getWidth(), oFileProperties.getResizePicture().getHeight()).toFile(destFile);
            return destFile;
        }

        //oversize = true 需要判断原始图片的宽度和高度，只有大于与设定值的时候才进行缩小操作
        if (bufferedImage.getWidth() > oFileProperties.getResizePicture().getWidth() || bufferedImage.getHeight() > oFileProperties.getResizePicture().getHeight()) {
            Thumbnails.of(bufferedImage).size(oFileProperties.getResizePicture().getWidth(), oFileProperties.getResizePicture().getHeight()).toFile(destFile);
        } else {
            Thumbnails.of(bufferedImage).scale(1).toFile(destFile);
            //mfile.transferTo(destFile);
        }
        return destFile;
    }
}
