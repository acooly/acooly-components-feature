/**
 * acooly-components-feature-parent
 * <p>
 * Copyright 2019 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhike
 * @date 2019-09-05 17:47
 */
package com.acooly.module.certification.enums;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

/**
 * @author zhike
 * @date 2019-09-05 17:47
 */
@Data
public class EnterpriseBusinessInfoResult extends ResultBase {

    /**
     * 工商名称
     */
    private String companyName;

    /**
     * 曾用名
     */
    private String companyNameold;

    /**
     * 公司名称-英文名
     */
    private String companyNameEn;

    /**
     * 信用代码
     */
    private String creditCode;

    /**
     * 组织机构代码
     */
    private String companyCode;
    /**
     * 注册号
     */
    private String regNumber;
    /**
     *
     */
    private String taxNumber;
    /**
     *工商类型
     */
    private String regType;
    /**
     * 登记机关
     */
    private String regOrgName;
    /**
     * 法人
     */
    private String faRen;
    /**
     * 法人类型取值公司、法人
     */
    private String companyType;
    /**
     * 企业状态
     */
    private String businessStatus;
    /**
     * 核准日期
     */
    private String chkDate;
    /**
     * 成立时间
     */
    private String issueTime;
    /**
     * 营业期限自
     */
    private String regDate;
    /**
     * 营业期限至
     */
    private String bussiness;
    /**
     * 注销日期
     */
    private String cancelDate;
    /**
     * 注册资金
     */
    private String regMoney;
    /**
     * 注册地址
     */
    private String address;
    /**
     * 经营范围
     */
    private String bussinessDes;
    /**
     * 电话
     */
    private String phone;
    /**
     *
     */
    private String phonelist;
    /**
     * 邮箱
     */
    private String email;
    /**
     *
     */
    private String emaillist;
    /**
     * 网址
     */
    private String webSite;
    /**
     *
     */
    private String webSitelist;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 所属机构
     */
    private String industry;
}
