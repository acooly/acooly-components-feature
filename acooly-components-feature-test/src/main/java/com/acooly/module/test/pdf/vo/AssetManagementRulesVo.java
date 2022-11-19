package com.acooly.module.test.pdf.vo;

import com.acooly.module.pdf.vo.AbstractDocumentVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 房屋买卖运营规则
 * @author xiyang@acooly.cn
 * @date 2018-09-28 15:15
 */
@Slf4j
@Data
public class AssetManagementRulesVo extends AbstractDocumentVo {
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 持有人
     */
    private List<Saler> salers;

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
     * 代持公司所属市
     */
    private String holdCity;

    /**
     * 租赁信息
     */
    private AssetShop assetShop;
}
