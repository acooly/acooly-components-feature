/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-07 23:13 创建
 */
package com.acooly.module.safety.support;

/**
 * @author zhangpu 2017-10-07 23:13
 */
public interface StringCodec {

    String encode(byte[] data);

    byte[] decode(String data);

}
