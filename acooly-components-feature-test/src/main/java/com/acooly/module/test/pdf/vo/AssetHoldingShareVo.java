package com.acooly.module.test.pdf.vo;

import com.acooly.module.pdf.vo.AbstractDocumentVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 房产份额持有确认函
 * @author xiyang@acooly.cn
 * @date 2018-10-19 11:10
 */
@Getter
@Setter
public class AssetHoldingShareVo extends AbstractDocumentVo {
    /**
     * 持有人ID
     */
    private String holderId;

    /**
     * 持有率(%)
     */
    private String holdRate;

    /**
     * 产权证号
     */
    private String certId;

    /**
     * 持有份数
     */
    private String holdShares;

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
     * 持有日期(原子交易日期)
     */
    private String holdDate;
}
