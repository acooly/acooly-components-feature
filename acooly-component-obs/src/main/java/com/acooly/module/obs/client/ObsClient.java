package com.acooly.module.obs.client;

import com.acooly.module.obs.common.model.ObjectMetadata;
import com.acooly.module.obs.common.model.ObjectResult;
import com.acooly.module.obs.common.model.ObsObject;
import com.acooly.module.obs.exceptions.ClientException;
import com.acooly.module.obs.exceptions.ObsException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * @author shuijing
 */
public interface ObsClient {

    ObjectResult putObject(String bucketName, String key, File file)
            throws ObsException, ClientException;

    ObjectResult putObject(String bucketName, String key, InputStream input)
            throws ObsException, ClientException;

    ObjectResult putObject(String bucketName, String key, File file, ObjectMetadata metadata)
            throws ObsException, ClientException;

    ObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws ObsException, ClientException;

    ObjectResult putObject(URL signedUrl, File file, Map<String, String> requestHeaders)
            throws ObsException, ClientException;

    ObjectResult putObject(URL signedUrl, InputStream inputStream, Map<String, String> requestHeaders)
            throws ObsException, ClientException;

    ObsObject getObject(String bucketName, String key) throws ObsException, ClientException;

    void deleteObject(String bucketName, String key) throws ObsException, ClientException;

    String getProvider();
}
