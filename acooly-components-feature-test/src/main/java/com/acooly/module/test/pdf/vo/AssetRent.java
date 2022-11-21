package com.acooly.module.test.pdf.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 租赁信息
 * @author xiaohong@acooly.cn
 * @date 2018-10-19 17:48
 */
@Getter
@Setter
public class AssetRent implements Serializable {
    /**
     * 承租人
     */
    private String renter;

    /**
     * 租赁开始日期
     */
    private String beginTime;

    /**
     * 租赁结束日期
     */
    private String endTime;

    /**
     * 租金
     */
    private String rentAmount;

    /**
     * 租金大写
     */
    private String rentAmountCH;

    /**
     * 租金支付方式(收租周期)
     */
    private String rentCycle;

    /**
     * 已支付押金,保证金
     */
    private String paidAmount;

    /**
     * 已支付押金,保证金大写
     */
    private String paidAmountCH;
}
