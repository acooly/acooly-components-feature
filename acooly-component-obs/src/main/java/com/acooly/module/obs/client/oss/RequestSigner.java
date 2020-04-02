package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.exceptions.ClientException;

/**
 * @author shuijing
 */
public interface RequestSigner {

    void sign(RequestMessage request) throws ClientException;
}
