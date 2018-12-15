package com.acooly.core.test.vo;

import com.acooly.module.pdf.vo.AbstractDocumentVo;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 测试pdf数据VO
 */
@Getter
@Setter
public class PdfDemoVo extends AbstractDocumentVo {

    private String policyNo;

    private String holderName;

    private String relation;

    private String holderAdress;

    private String holderPostCode;

    private String insuredName;

    private String insuredPingyinName;

    private String insuredPassportNo;

    private String insuredSex;

    private String insuredBirthday;

    private String insuredPhone;

    private String insuredIDNo;

    private String destination;

    private String beneficiaryName;

    private String remarks;

    private String period;

    private String accidentalSumInsured;

    private String emergencySumInsured;

    private String medicalSumInsured;

    private String premium;

    private String issueDate;

    private String branchName;

    private String companyName;

    private Map<String, String> names = Maps.newHashMap();
}
