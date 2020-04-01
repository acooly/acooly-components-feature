package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.common.util.OSSUtils;
import com.acooly.module.obs.common.util.SignUtils;
import com.acooly.module.obs.exceptions.ClientException;

/**
 * @author shuijing
 */
public class OSSRequestSigner implements RequestSigner {

    private String httpMethod;

    /* Note that resource path should not have been url-encoded. */
    private String resourcePath;
    private ObsProperties obsProperties;

    public OSSRequestSigner(String httpMethod, String resourcePath, ObsProperties properties) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.obsProperties = properties;
    }

    @Override
    public void sign(RequestMessage request) throws ClientException {
        String accessKeyId = obsProperties.getAliyun().getAccessKeyId();
        String secretAccessKey = obsProperties.getAliyun().getAccessKeySecret();

        if (accessKeyId.length() > 0 && secretAccessKey.length() > 0) {
            String canonicalString =
                    SignUtils.buildCanonicalString(httpMethod, resourcePath, request, null);
            String signature =
                    ServiceSignature.create().computeSignature(secretAccessKey, canonicalString);
            request.addHeader(
                    OSSHeaders.AUTHORIZATION, OSSUtils.composeRequestAuthorization(accessKeyId, signature));
        }
    }
}
