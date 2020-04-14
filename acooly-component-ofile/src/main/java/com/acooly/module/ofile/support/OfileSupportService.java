package com.acooly.module.ofile.support;

import com.acooly.module.ofile.enums.OFileType;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author liangsong
 * @date 2020-04-13 13:31
 */
public interface OfileSupportService {

    /*==============文件上传通用能力start=================*/
    /**
     * 存储模式
     * @param request
     * @return
     */
    String getStorageType(HttpServletRequest request);

    /**
     * 获取模块名称
     *
     * @param request
     * @return
     */
    String getModule(HttpServletRequest request);

    /**
     * 元数据
     *
     * @param request
     * @return
     */
    String getMetadata(HttpServletRequest request);

    /**
     * 获取缩略图大小
     * @param request
     * @return
     */
    int getThumbnailSize(HttpServletRequest request);
    /*==============文件上传通用能力end=================*/

    /*==============以下为obs上传能力=================*/
    /**
     * sts临时授权url过期时间
     *
     * @return
     */
    Date getExpireDate();

    /**
     * 下载时的文件处理，可根据文件类型进行不同的处理
     * 图片处理 https://help.aliyun.com/document_detail/44686.html?spm=a2c4g.11186623.2.32.343b78159XtPmm#concept-m4f-dcn-vdb
     * 视频处理 https://help.aliyun.com/document_detail/64555.html?spm=a2c4g.11186623.6.1447.3eaa6dd774vWhH
     *
     * @param oFileType
     * @return
     */
    String getProcess(OFileType oFileType);

    /**
     * 生成key值的规则
     *
     * @param inputName
     * @param originalFilename
     * @return
     */
    String generateKey(HttpServletRequest request, String inputName, String originalFilename);

    /**
     * bucketName，默认为空，由obs组件配置使用默认bucket
     *
     * @return
     */
    String getBucketName(HttpServletRequest request);

}
