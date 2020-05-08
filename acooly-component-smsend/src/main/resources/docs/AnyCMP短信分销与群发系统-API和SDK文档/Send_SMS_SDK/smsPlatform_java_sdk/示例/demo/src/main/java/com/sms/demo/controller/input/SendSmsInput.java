package com.sms.demo.controller.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 说明: 单发送短信实体
 * <br>@author zhangbo
 * <br>@date 2019/5/23 17:37
 *
 * <br>UpdateNote:
 * <br>UpdateTime:
 * <br>UpdateUser:
 */
@ApiModel("单发送短信实体")
public class SendSmsInput {

    @ApiModelProperty(value = "发送手机号")
    private String PhoneNumbers;
    @ApiModelProperty(value = "签名")
    private String SignName;
    @ApiModelProperty(value = "模板编码")
    private String TemplateCode;
    @ApiModelProperty(value = "参数")
    private String TemplateParam;
    @ApiModelProperty(value = "AccessKeyId")
    private String AccessKeyId;
    @ApiModelProperty(value = "SecretKeyId")
    private String SecretKeyId;
    @ApiModelProperty(value = "低于")
    private String RegionId;

    public String getPhoneNumbers() {
        return PhoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
    }

    public String getTemplateParam() {
        return TemplateParam;
    }

    public void setTemplateParam(String templateParam) {
        TemplateParam = templateParam;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getSecretKeyId() {
        return SecretKeyId;
    }

    public void setSecretKeyId(String secretKeyId) {
        SecretKeyId = secretKeyId;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }
}
