package com.acooly.module.test.pdf.vo;

import com.acooly.module.pdf.vo.AbstractDocumentVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 房屋买卖四方合同
 * @author xiaohong@acooly.cn
 * @date 2018-10-19 13:57
 */
@Getter
@Setter
public class AssetSalesContractVo extends AbstractDocumentVo {
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 出售人ID
     */
    private String salerId;
    /**
     * 购买人
     */
    private List<Purchaser> purchasers;

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
     * 运营公司
     */
    private String operationCompany;

    /**
     * 商铺信息
     */
    private AssetShop assetShop;

    /**
     * 租赁信息
     */
    private AssetRent assetRent;

    /**
     * 标的房产出售价格
     */
    private String saleAmount;

    /**
     * 标的房产出售价格大写
     */
    private String saleAmountCH;

    /**
     * 份数
     */
    private String shares;

    /**
     * 每份单价
     */
    private String shareAmount;

    /**
     * 合计持有份数
     */
    private String totalHoldSumShares;

    /**
     * 合计持有金额（元）
     */
    private String totalHoldSumAmount;

    /**
     * 房屋转让期（日）
     */
    private String houseAssignPeriod;
}
