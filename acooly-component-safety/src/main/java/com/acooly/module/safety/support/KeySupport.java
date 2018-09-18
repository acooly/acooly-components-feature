/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-16 11:15 创建
 */
package com.acooly.module.safety.support;

import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * @author zhangpu 2017-11-16 11:15
 */
@Slf4j
public abstract class KeySupport {

    /**
     * load key content from keyfile or string
     *
     * @param key
     * @param fileEncoding
     * @return
     */
    protected String loadKeyContent(String key, String fileEncoding) {
        if (!Strings.startsWith(key, "classpath")
                && !Strings.startsWith(key, "file")
                && !Strings.startsWith(key, "/")) {
            return key;
        }
        if (Strings.isBlank(fileEncoding)) {
            fileEncoding = "UTF-8";
        }
        try {
            Resource resource = new DefaultResourceLoader().getResource(key);
            String keyContent = IOUtils.toString(resource.getInputStream(), fileEncoding);
            log.debug("load key from file:{}, content:\n{}", key, keyContent);
            return keyContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
