/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-10
 */
package com.acooly.module.certification.platform.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.certification.enums.CertTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 银行卡四要素记录表 Entity
 *
 * @author shuijing
 * Date: 2017-08-10 18:15:53
 */
@Data
@Entity
@Table(name = "bank_certification_record")
public class BankCertificationRecord extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 持卡人姓名
     */
    @NotBlank
    @Size(max = 20)
    private String realName;

    /**
     * 银行卡帐号
     */
    @NotBlank
    @Size(max = 32)
    private String cardNo;

    /**
     * 开卡使用的证件号码
     */
    @Size(max = 18)
    private String certId;

    /**
     * 绑定手机号
     */
    @Size(max = 32)
    private String phoneNum;

    /**
     * 开卡使用的证件类型
     */
    @Enumerated(EnumType.STRING)
    private CertTypeEnum certType;

    /**
     * 银行卡归属地
     */
    @Size(max = 32)
    private String belongArea;

    /**
     * 银行客服
     */
    @Size(max = 32)
    private String bankTel;

    /**
     * 银行卡产品名称
     */
    @Size(max = 32)
    private String brand;

    /**
     * 银行名称
     */
    @Size(max = 64)
    private String bankName;

    /**
     * 银行卡种
     */
    @Size(max = 32)
    private String cardType;

    /**
     * 银行官网
     */
    @Size(max = 32)
    private String bankUrl;

    @NotBlank
    @Size(max = 100)
    private String failReason;

    /**
     * 状态
     */
    private Integer status = 0;

}
