/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.module.ofile;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.Strings;
import com.acooly.module.ofile.enums.StorageTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.File;

import static com.acooly.module.ofile.OFileProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
public class OFileProperties implements InitializingBean {
    public static final String PREFIX = "acooly.ofile";

    /**
     * 是否物理删除文件
     * <p>
     * true： 物理删除
     * <p>
     * false：逻辑删除
     */
    public boolean fileDelete = true;


    /**
     * ObjectId 是否唯一
     *
     * true：唯一
     *
     * false：不唯一（阿里云上传相同的文件-ObjectId相同）
     */
    public boolean onlyObjectId = false;


    /**
     * 存储文件类型
     */
    public String storageType = StorageTypeEnum.LOCAL.getCode();
    /**
     * 文件访问路径,可以配置为域名的形式，建议为//www.test.com/media,不区分协议
     */
    private String serverRoot = "/media";
    /**
     * 文件存储路径
     */
    private String storageRoot = "/data/media/";

    /**
     * 存储命名空间，默认为空，如果填写，文件存储路径会变为：storageRoot/storageNameSpace，如：/data/media/taodai
     */
    private String storageNameSpace;
    /**
     * 上传的文件类型
     */
    private String allowExtentions = "txt,zip,csv,xls,word,jpg,jpeg,gif,png";
    private long maxSize = 5242880;
    private int thumbnailSize = 200;

    /**
     * 访问静态资源是否认证
     * （如果开启，则与上传的认证验证方式一致：session,sign）
     */
    private boolean accessAuthEnable = false;
    private boolean checkSession = false;
    private String checkSessionKey = "sessionCustomer,sessionUser,org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY";
    /**
     * 可配置的内置文件上传签名认证器
     */
    private boolean configuredSignAuthEnable = false;
    /**
     * 可配置的内置文件上传签名认证器: 访问吗
     */
    private String configuredSignAuthAccessKey = "configuredSignAuthAccessKey";
    /**
     * 可配置的内置文件上传签名认证器: 安全码（秘钥）
     */
    private String configuredSignAuthSecretKey = "configuredSignAuthSecretKey";

    private boolean checkReferer = true;
    private boolean enableLocalMapping = true;


    /**
     * 开启后，上传的图片自动加水印图片，上传请求参数需要传入 watermarkImage = true
     */
    private Watermarkimage watermarkimage = new Watermarkimage();

    /**
     * 开启后，上传的图片自动加水印文字，上传请求参数需要传入 watermarkText = true
     */
    private Watermarktext watermarktext = new Watermarktext();

    /**
     * 开启后，上传的图片自动进行缩放
     */
    private ResizePicture resizePicture = new ResizePicture();


    public String getStorageRoot() {
        return storageRoot;
    }

    public String getServerRoot() {
        return serverRoot;
    }


    /**
     * 获取可访问的全URL
     *
     * @param fileStoragePath 存在在数据库中的相对路径
     * @return
     */
    public String getAccessableUrl(String fileStoragePath) {
        if (Strings.isBlank(fileStoragePath)) {
            return fileStoragePath;
        }
        String relativePath = Strings.replaceAll(fileStoragePath, "\\\\", "/");
        if (Strings.isHttpUrl(relativePath)) {
            return relativePath;
        }
        String fullUrl = getServerRoot();
        if (!Strings.endsWith(fullUrl, "/")) {
            fullUrl = fullUrl + "/";
        }
        return fullUrl + Strings.removeStart(relativePath, "/");
    }

    String getServerRootMappingPath() {
        String path = "";
        boolean containDomain = false;
        if (serverRoot.startsWith("http://")) {
            path = serverRoot.substring(7);
            containDomain = true;
        }
        if (serverRoot.startsWith("https://")) {
            path = serverRoot.substring(8);
            containDomain = true;
        }
        if (serverRoot.startsWith("//")) {
            path = serverRoot.substring(2);
            containDomain = true;
        }
        if (containDomain) {
            int idx = path.indexOf('/');
            if (idx < 0) {
                if (enableLocalMapping) {
                    throw new AppConfigException("当启用本地tomcat映射时，必须指定访问域名路径，比如：http://res.example.com/media");
                }
            }
            path = path.substring(path.indexOf('/'));
        } else {
            path = serverRoot;
        }
        return path;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(serverRoot);
        if (serverRoot.endsWith("/")) {
            serverRoot = serverRoot.substring(0, serverRoot.length() - 1);
        }
        Assert.notNull(storageRoot);
        if (SystemUtils.IS_OS_WINDOWS) {
            if (!new File(storageRoot).isAbsolute()) {
                storageRoot = new File(storageRoot).getAbsolutePath();
            }
            if (!storageRoot.contains(":") || !storageRoot.contains("\\")) {
                log.error("windows配置路径格式应该为:d:\\\\data\\\\projects");
                Apps.shutdown();
            }
            if (!storageRoot.endsWith("\\")) {
                storageRoot += "\\";
            }
        } else {
            if (!storageRoot.endsWith("/")) {
                storageRoot += "/";
            }
        }
    }

    @Data
    public static class Watermarkimage {
        /**
         * 开启后，上传的图片自动加水印图片，上传请求参数需要传入 watermarkImage = true
         */
        private boolean enable = true;
        /**
         * 水印图片
         */
        private String markImageFilePath;
        /**
         * 位置x轴
         */
        private int x = 0;
        /**
         * 位置y轴
         */
        private int y = 0;
    }

    @Data
    public static class Watermarktext {
        /**
         * 开启后，上传的图片自动加水印文字，上传请求参数需要传入 watermarkText = true
         */
        private boolean enable = true;
        /**
         * 水印文字
         */
        private String markText;
        /**
         * 位置x轴
         */
        private int x = 10;
        /**
         * 位置y轴
         */
        private int y = 10;
        /**
         * 字体大小
         */
        private int fontSize = 16;
        /**
         * 透明度 0-1
         */
        private float alpha = 1F;
    }

    @Data
    public static class ResizePicture {
        /**
         * 开启图片缩放功能，默认为false
         */
        private boolean enable = false;

        /**
         * #oversize=true，如果原始图片宽或高小于设定值，不进行操作，大于设定值则对图片进行缩小操作。
         * #oversize=false，如果原始图片宽或高小于设定值，对图片进行放大操作，大于设定值则对图片进行缩小操作。
         * #默认值为true
         */
        private boolean oversize = true;


        /**
         * 图片宽X(px),默认2000
         */
        private int width = 2000;
        /**
         * 图片高X(px),默认2000
         */
        private int height = 2000;
    }


}
