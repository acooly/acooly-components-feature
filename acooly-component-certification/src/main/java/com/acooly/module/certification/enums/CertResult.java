/*
 * 修订记录:
 * zhike@yiji.com 2017-04-21 16:06 创建
 *
 */
package com.acooly.module.certification.enums;

import lombok.Data;

/**
 * 修订记录：
 *
 * @author zhike@yiji.com
 */
@Data
public class CertResult {

    /**
     * 结果编码
     */
    private String resultCode;

    /**
     * 结果信息
     */
    private String resultMessage;

    /**
     * 性别
     */
    private String sex;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 户籍所在地
     */
    private String address;
}
