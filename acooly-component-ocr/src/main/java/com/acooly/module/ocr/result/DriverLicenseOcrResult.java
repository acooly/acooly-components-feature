package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liangsong
 * @date 2020-03-26 09:40
 */
@Data
public class DriverLicenseOcrResult extends ResultBase {

    @ApiModelProperty(value = "百度流水号",required = false)
    private String logId;

    @ApiModelProperty(value = "姓名",required = false)
    private String name;

    @ApiModelProperty(value = "身份证号",required = false)
    private String idCardNo;

    @ApiModelProperty(value = "性别",required = false)
    private String gender;

    @ApiModelProperty(value = "国籍",required = false)
    private String nationality;

    @ApiModelProperty(value = "住址",required = false)
    private String address;

    @ApiModelProperty(value = "出生日期,格式yyyy-MM-dd",required = false)
    private String birthday;

    @ApiModelProperty(value = "初次领证日期,格式yyyy-MM-dd",required = false)
    private String firstIssueDate;

    @ApiModelProperty(value = "准驾车型",required = false)
    private String type;

    @ApiModelProperty(value = "有效起始日期,格式yyyy-MM-dd",required = false)
    private String validFrom;

    @ApiModelProperty(value = "有效期限长度, 或者有效期限截止日期,新老驾驶证识别结果不一样，截止日期有可能是日期格式，也有可能是6年，10年，长期",required = false)
    private String validTo;
}
