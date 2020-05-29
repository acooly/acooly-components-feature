package com.acooly.module.obs.client;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Strings;
import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.common.OssFile;
import com.acooly.module.obs.common.OssUtils;
import com.acooly.module.obs.dto.SecurityTokenDto;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author shuijing
 */
@Slf4j
@Service("aliyunObsClient")
public class AliyunObsClient extends AbstractObsClient {

    @Autowired
    private ObsProperties properties;

    @Override
    public String getProvider() {
        return "阿里OSS";
    }

    @Override
    public OssFile putObject(String bucketName, String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        return upload(putObjectRequest);
    }

    @Override
    public OssFile putObject(String bucketName, String key, InputStream input) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input);
        return upload(putObjectRequest);
    }

    @Override
    public String getUrlByKey(String key) {
        return OssUtils.getUrlByKey(properties.getAliyun().getProtocol(),
                properties.getAliyun().getBucketName(), properties.getAliyun().getEndpoint(), key);
    }

    @Override
    public String getAccessUrlBySts(String bucketName, String key, String processStyle, Date expireDate) {
        if (Strings.isBlank(bucketName)) {
            bucketName = properties.getAliyun().getBucketName();
        }
        //post形式获取token
        SecurityTokenDto securityTokenDto = getSecurityToken(MethodType.POST);
        //get形式获取url，必须为get，用post访问获取的url只能通过post访问
        String url = getAuthorization(securityTokenDto, HttpMethod.GET, bucketName, key, expireDate, processStyle);
        log.info("返回可访问的url={}", url);
        return url;
    }

    @Override
    public OssFile putObject(String bucketName, String key, File file, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file, metadata);
        return upload(putObjectRequest);
    }

    @Override
    public OssFile putObject(
            String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input, metadata);
        return upload(putObjectRequest);
    }

    @Override
    public OssFile getObject(String bucketName, String key, String processStyle) {
        OSS client = getOssClient();
        OssFile ossFile = new OssFile();
        if (Strings.isBlank(bucketName)) {
            bucketName = properties.getAliyun().getBucketName();
        }
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        getObjectRequest.setProcess(processStyle);
        OSSObject ossObject = client.getObject(getObjectRequest);
        ossFile.setETag(ossObject.getKey());
        ossFile.setMetadata(ossObject.getObjectMetadata());
        ossFile.setUrl(OssUtils.getUrlByKey(properties.getAliyun().getProtocol(),
                properties.getAliyun().getBucketName(), properties.getAliyun().getEndpoint(), key));
        ossFile.setFileInputStream(ossObject.getObjectContent());
        return ossFile;
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        OSS client = getOssClient();
        client.deleteObject(bucketName, key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private OssFile upload(PutObjectRequest putObjectRequest) {
        OSS client = getOssClient();
        OssFile ossFile = new OssFile();
        if (Strings.isBlank(putObjectRequest.getBucketName())) {
            putObjectRequest.setBucketName(properties.getAliyun().getBucketName());
        }
        PutObjectResult putObject = null;
        try {
            putObject = client.putObject(putObjectRequest);
            ossFile.setETag(putObject.getETag());
            ossFile.setKey(putObjectRequest.getKey());
            ossFile.setUrl(properties.getAliyun().getProtocol()
                    .concat(properties.getAliyun().getBucketName())
                    .concat(".")
                    .concat(properties.getAliyun().getEndpoint())
                    .concat("/").concat(putObjectRequest.getKey()));
            client.shutdown();
        } catch (OSSException e) {
            client.shutdown();
            throw new BusinessException("文件上传失败");
        }
        return ossFile;
    }

    public String getAuthorization(SecurityTokenDto dto, HttpMethod method, String bucketName, String key, Date expireDate, String processStyle) {
        log.info("getAuthorization   start");
        OSS oss = new OSSClientBuilder().build(properties.getAliyun().getEndpoint(),
                dto.getAccessKeyId(), dto.getAccessKeySecret(), dto.getSecurityToken());
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, method);
        if (expireDate == null) {
            req.setExpiration(new Date(System.currentTimeMillis() + properties.getAliyun().getStsExpiresTime() * 1000));
        } else {
            req.setExpiration(expireDate);
        }
        req.setProcess(processStyle);
        URL url = oss.generatePresignedUrl(req);
        oss.shutdown();
        log.info("getAuthorization   end:{}", url.toString());
        return url.toString();
    }

    public SecurityTokenDto getSecurityToken(MethodType methodType) {
        log.info("getSecurityToken   start");
        SecurityTokenDto dto = new SecurityTokenDto();
        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = properties.getAliyun().getStsRoleArn();
        String roleSessionName = properties.getAliyun().getStsRoleSessionName();
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
            // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
            // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile(properties.getAliyun().getRegionId(),
                    properties.getAliyun().getStsAccessKeyId(), properties.getAliyun().getStsAccessKeySecret());
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(properties.getAliyun().getStsApiVersion());
            request.setMethod(methodType);
            // 此处必须为 HTTPS
            request.setProtocol(ProtocolType.HTTPS);
            // RoleArn 需要在 RAM 控制台上获取
            request.setRoleArn(roleArn);
            // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
            // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
            // 具体规则请参考API文档中的格式要求
            request.setRoleSessionName(roleSessionName);
            // 授权策略
            final AssumeRoleResponse response = client.getAcsResponse(request);
            dto.setAccessKeyId(response.getCredentials().getAccessKeyId());
            dto.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
            dto.setExpiration(response.getCredentials().getExpiration());
            dto.setSecurityToken(response.getCredentials().getSecurityToken());
            log.info("getSecurityToken: {}", JSONObject.toJSONString(dto));
            log.info("getSecurityToken   end");

        } catch (ClientException e) {
            log.error("ClientException   e:{}", e);
        }
        return dto;
    }

    private OSS getOssClient() {
        return new OSSClientBuilder().build(properties.getAliyun().getEndpoint(), properties.getAliyun().getAccessKeyId(), properties.getAliyun().getAccessKeySecret());
    }

}
