package com.acooly.module.obs.client;

import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.client.oss.OSSObjectOperation;
import com.acooly.module.obs.client.oss.model.GenericRequest;
import com.acooly.module.obs.client.oss.model.GetObjectRequest;
import com.acooly.module.obs.client.oss.model.PutObjectRequest;
import com.acooly.module.obs.client.oss.model.PutObjectResult;
import com.acooly.module.obs.common.model.ObjectMetadata;
import com.acooly.module.obs.common.model.ObjectResult;
import com.acooly.module.obs.common.model.ObsObject;
import com.acooly.module.obs.exceptions.ClientException;
import com.acooly.module.obs.exceptions.ObsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static com.acooly.module.obs.client.oss.OSSConstants.DEFAULT_OSS_VISITE_URL;
import static com.acooly.module.obs.client.oss.OSSConstants.PROTOCOL_HTTP;
import static com.acooly.module.obs.client.oss.ResponseMessage.HTTP_SUCCESS_STATUS_CODE;

/**
 * @author shuijing
 */
@Slf4j
@Service("aliyunObsClient")
public class AliyunObsClient extends AbstractObsClient {

    @Autowired
    private ObsProperties properties;

    private OSSObjectOperation objectOperation;

    @Override
    public String getProvider() {
        return "阿里OSS";
    }

    protected ObjectResult convertResult(PutObjectResult putObjectResult, String key) {
        ObjectResult result = new ObjectResult();
        int statusCode = putObjectResult.getResponse().getStatusCode();
        if (statusCode != HTTP_SUCCESS_STATUS_CODE) {
            result.setStatus(ResultStatus.failure);
            result.setDetail(putObjectResult.getResponse().getErrorResponseAsString());
        } else {
            result.setStatus(ResultStatus.success);
            result.setDetail(putObjectResult.getETag());
        }
        result.setCode(String.valueOf(putObjectResult.getResponse().getStatusCode()));
        //http://yijifu-acooly.oss.aliyuncs.com/test/logo.png
        String url =
                PROTOCOL_HTTP + putObjectResult.getBuketName() + "." + DEFAULT_OSS_VISITE_URL + "/" + key;
        result.setUrl(url);
        log.info("put result:{}", result.toString());
        return result;
    }

    @Override
    public ObjectResult putObject(String bucketName, String key, File file)
            throws ObsException, ClientException {
        PutObjectResult putObjectResult =
                objectOperation.putObject(new PutObjectRequest(bucketName, key, file, null));
        return convertResult(putObjectResult, key);
    }

    @Override
    public ObjectResult putObject(String bucketName, String key, InputStream input)
            throws ObsException, ClientException {
        PutObjectResult putObjectResult =
                objectOperation.putObject(new PutObjectRequest(bucketName, key, input));
        return convertResult(putObjectResult, key);
    }

    @Override
    public ObjectResult putObject(String bucketName, String key, File file, ObjectMetadata metadata)
            throws ObsException, ClientException {
        PutObjectResult putObjectResult =
                objectOperation.putObject(new PutObjectRequest(bucketName, key, file, metadata));
        return convertResult(putObjectResult, key);
    }

    @Override
    public ObjectResult putObject(
            String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws ObsException, ClientException {
        PutObjectResult putObjectResult =
                objectOperation.putObject(new PutObjectRequest(bucketName, key, input, metadata));
        return convertResult(putObjectResult, key);
    }

    @Override
    public ObjectResult putObject(URL signedUrl, File file, Map<String, String> requestHeaders)
            throws ObsException, ClientException {
        return null;
    }

    @Override
    public ObjectResult putObject(
            URL signedUrl, InputStream inputStream, Map<String, String> requestHeaders)
            throws ObsException, ClientException {
        return null;
    }

    @Override
    public ObsObject getObject(String bucketName, String key) throws ObsException, ClientException {
        return objectOperation.getObject(new GetObjectRequest(bucketName, key));
    }

    @Override
    public void deleteObject(String bucketName, String key) throws ObsException, ClientException {
        objectOperation.deleteObject(new GenericRequest(bucketName, key));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.objectOperation = new OSSObjectOperation(properties);
    }
}
