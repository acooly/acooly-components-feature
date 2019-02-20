package com.acooly.module.security.service;

/**
 * @author shuijing
 */
public interface SSOAuthzService {
    boolean permitted(String uri, String username) throws Exception;
}
