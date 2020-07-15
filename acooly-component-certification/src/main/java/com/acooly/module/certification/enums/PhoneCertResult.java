package com.acooly.module.certification.enums;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;
import lombok.ToString;

/**
 * @author liangsong
 * @date 2020-07-15 14:28
 */
@Data
@ToString(callSuper = true)
public class PhoneCertResult extends ResultBase {

    /**
     * 归属地（省）
     */
    private String prov;

    /**
     * 归属地（市）
     */
    private String city;

    /**
     * 手机号段
     */
    private String num;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 省编码
     */
    private String provCode;

    /**
     * 运营商（例如：电信CDMA卡）
     */
    private String name;

    /**
     * 1为移动  2为电信  3为联通
     */
    private int type;
}
