package com.acooly.module.test.pdf.vo;

import com.acooly.module.pdf.vo.AbstractDocumentVo;
import lombok.Data;

/**
 * 房屋份额转让合同
 * @author xiyang@acooly.cn
 * @date 2018-09-19 17:17
 */
@Data
public class AtomTradeVo extends AbstractDocumentVo {
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 房屋买卖四方合同编号
     */
    private String saleContractNo;
    /**
     * 持有人ID
     */
    private String holderId;

    /**
     * 购买者ID
     */
    private String purchaserId;

    /**
     * 代持人
     */
    private String holdingPerson;

    /**
     * 代持人ID
     */
    private String holdingPersonId;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 住所地
     */
    private String legalPersonAddress;

    /**
     * 转让日期：年
     */
    private String year;

    /**
     * 转让日期：月
     */
    private String month;

    /**
     * 转让日期：日
     */
    private String day;

    /**
     * 持有率(%)
     */
    private String holdRate;

    /**
     * 持有份额
     */
    private String holdShares;

    /**
     * 运营公司
     */
    private String operationCompany;

    /**
     * 资管公司
     */
    private String manageCompany;

    /**
     * 商铺信息
     */
    private AssetShop assetShop;

    /**
     * 租赁信息
     */
    private AssetRent assetRent;

    /**
     * 转让价格
     */
    private String tradeAmount;

    /**
     *转让价格大写
     */
    private String tradeAmountCH;

    /**
     * 标的房产出售价格
     */
    private String saleAmount;

    /**
     * 标的房产出售价格大写
     */
    private String saleAmountCH;
}
