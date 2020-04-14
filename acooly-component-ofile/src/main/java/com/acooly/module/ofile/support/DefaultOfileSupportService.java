package com.acooly.module.ofile.support;

import com.acooly.core.utils.Strings;
import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.common.OssUtils;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.enums.OFileType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author liangsong
 * @date 2020-04-13 09:29
 */
public class DefaultOfileSupportService implements OfileSupportService {

    /*======================ofile相关自定义请求参数=========================*/
    /**
     * 存储模式
     */
    private static final String STORAGE_TYPE = "storageType";
    /**
     * 桶名称
     */
    private static final String BUCKET_NAME = "bucketName";
    /**
     * 模块名称
     */
    private static final String MODULE = "module";
    /**
     * 元数据
     */
    private static final String METADATA = "metadata";
    /**
     * 文件根路径
     */
    private static final String ROOT_PATH = "rootPath";

    private static final String THUMB_SIZE = "thumbSize";

    @Autowired
    private OFileProperties oFileProperties;

    @Autowired
    private ObsProperties obsProperties;

    @Override
    public String getStorageType(HttpServletRequest request) {
        String storageType = request.getParameter(STORAGE_TYPE);
        if (StringUtils.isBlank(storageType)) {
            return oFileProperties.getStorageType();
        }
        return storageType;
    }

    @Override
    public String getModule(HttpServletRequest request) {
        String module = request.getParameter(MODULE);
        return Strings.trimToNull(module);
    }

    @Override
    public String getMetadata(HttpServletRequest request) {
        String module = request.getParameter(METADATA);
        return Strings.trimToNull(module);
    }

    @Override
    public Date getExpireDate() {
        if (ObsProperties.Provider.Aliyun.equals(obsProperties.getProvider()) && obsProperties.getAliyun().isStsEnable()) {
            return new Date(System.currentTimeMillis() + obsProperties.getAliyun().getStsExpiresTime() * 1000);
        }
        return new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000);
    }

    @Override
    public String getProcess(OFileType oFileType) {
        if (OFileType.picture.equals(oFileType)) {
            return ("image/resize,w_").concat(String.valueOf(oFileProperties.getThumbnailSize()))
                    .concat(",").concat("h_").concat(String.valueOf(oFileProperties.getThumbnailSize()));
        }
        return null;
    }

    @Override
    public String generateKey(HttpServletRequest request, String inputName, String originalFilename) {
        String rootPath = request.getParameter(ROOT_PATH);
        return OssUtils.generateKey(rootPath, originalFilename);
    }

    @Override
    public String getBucketName(HttpServletRequest request) {
        String bucketName = request.getParameter(BUCKET_NAME);
        return bucketName;
    }

    @Override
    public int getThumbnailSize(HttpServletRequest request) {
        String thumbSize = request.getParameter(THUMB_SIZE);
        if (Strings.isNumber(thumbSize)) {
            return Integer.parseInt(thumbSize);
        }
        return oFileProperties.getThumbnailSize();
    }
}
