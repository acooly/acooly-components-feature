package com.acooly.module.safety.signature;

/**
 * 签名接口
 *
 * @param <T>
 * @author zhangpu
 * @date 2014年6月3日
 */
public interface Signer<T, K> {

    /**
     * 签名
     *
     * @param plain 明文
     * @param key   秘钥
     * @return
     */
    String sign(T plain, K key);

    /**
     * 认证失败抛出异常
     *
     * @param plain 明文
     * @param key   秘钥
     * @param sign  待验签名值
     */
    void verify(T plain, K key, String sign);

    /**
     * 签名类型
     *
     * @return
     */
    String getSinType();
}
