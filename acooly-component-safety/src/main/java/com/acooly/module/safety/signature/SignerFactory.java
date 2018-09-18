package com.acooly.module.safety.signature;

/**
 * 签名对象工厂
 *
 * @param <T>
 * @author zhangpu
 * @date 2014年6月3日
 */
public interface SignerFactory<T, K> {

    Signer<T, K> getSigner(String signType);

}
