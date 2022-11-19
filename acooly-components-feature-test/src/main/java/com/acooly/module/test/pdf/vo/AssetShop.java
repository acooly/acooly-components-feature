package com.acooly.module.test.pdf.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商铺信息
 * @author xiaohong@acooly.cn
 * @date 2018-10-19 15:32
 */
@Getter
@Setter
public class AssetShop implements Serializable {
    /**
     * 产权坐落
     */
    private String certAddress;

    /**
     * 建面面积（平方米）
     */
    private String area;

    /**
     * 房屋用途
     */
    private String certPurpose;

    /**
     * 产权证号
     */
    private String certId;

    /**
     * 所有权证号
     */
    private String certOwnerId;

    /**
     * 土地使用权证号
     */
    private String certLandId;

    /**
     * 是否租赁（Y/N）
     */
    private String rent;

    /**
     * 标的房产的其它信息
     */
    private String certDesc;

    /**
     * 是否装修（Y/N）
     */
    private String decorate;

    /**
     * 房屋获取方式
     */
    private String houseOwnMethod;

    /**
     * 获取土地使用权方式
     */
    private String landGetMethod;
}
