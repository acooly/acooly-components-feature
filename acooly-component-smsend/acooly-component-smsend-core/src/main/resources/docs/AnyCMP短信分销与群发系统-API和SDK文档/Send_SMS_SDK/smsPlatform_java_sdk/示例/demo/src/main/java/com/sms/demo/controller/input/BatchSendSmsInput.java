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
public class BatchSendSmsInput {

    @ApiModelProperty(value = "发送手机号")
    private String PhoneNumberJson;
    @ApiModelProperty(value = "签名")
    private String SignNameJson;
    @ApiModelProperty(value = "模板编码")
    private String TemplateCode;
    @ApiModelProperty(value = "参数")
    private String TemplateParamJson;
    @ApiModelProperty(value = "AccessKeyId")
    private String AccessKeyId;
    @ApiModelProperty(value = "SecretKeyId")
    private String SecretKeyId;
    @ApiModelProperty(value = "低于")
    private String RegionId;

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
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

    public String getPhoneNumberJson() {
        return PhoneNumberJson;
    }

    public void setPhoneNumberJson(String phoneNumberJson) {
        PhoneNumberJson = phoneNumberJson;
    }

    public String getSignNameJson() {
        return SignNameJson;
    }

    public void setSignNameJson(String signNameJson) {
        SignNameJson = signNameJson;
    }

    public String getTemplateParamJson() {
        return TemplateParamJson;
    }

    public void setTemplateParamJson(String templateParamJson) {
        TemplateParamJson = templateParamJson;
    }
}
