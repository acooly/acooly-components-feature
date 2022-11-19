package com.acooly.module.test.pdf.vo;

import com.acooly.core.utils.Money;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 购买者
 * @author xiaohong@acooly.cn
 * @date 2018-10-22 10:20
 */
@Getter
@Setter
public class Purchaser implements Serializable {
    public Purchaser(){}
    /**
     * @param userNo        用户编号
     * @param holdShares    持有份数
     * @param holdSumAmount 持有金额
     */
    public Purchaser(String userNo, Long holdShares, Money holdSumAmount){
        this.userNo = userNo;
        this.holdShares = holdShares;
        this.holdSumAmount = holdSumAmount.getAmount().longValue();
    }

    /**
     * @param userNo        用户编号
     * @param holdShares    持有份数
     * @param holdSumAmount 持有金额（元)
     */
    public Purchaser(String userNo, Long holdShares, Long holdSumAmount){
        this.userNo = userNo;
        this.holdShares = holdShares;
        this.holdSumAmount = holdSumAmount;
    }
    /**
     * 购买者ID
     */
    private String userNo;

    /**
     * 认购份额
     */
    private Long holdShares;

    /**
     * 认购款
     */
    private Long holdSumAmount;
}
