package com.acooly.module.certification.enums;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

/**
 * @author shuijing
 */
@Data
public class BankCardResult extends ResultBase {

    /**
     * 银行卡归属地
     */
    private String belongArea;

    /**
     * 银行客服
     */
    private String bankTel;

    /**
     * 银行卡产品名称
     */
    private String brand;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡种
     */
    private String cardType;

    /**
     * 银行官网
     */
    private String bankUrl;

    /**
     * 银行卡帐号
     */
    private String cardNo;
}
