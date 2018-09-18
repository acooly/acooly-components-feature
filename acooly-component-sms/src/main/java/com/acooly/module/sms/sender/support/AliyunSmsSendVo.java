package com.acooly.module.sms.sender.support;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author shuijing
 */
public class AliyunSmsSendVo extends BaseSmsSendVo {

    /**
     * 短信签名
     */
    @Getter
    @Setter
    private String FreeSignName;
    /**
     * 模板CODE
     */
    @Getter
    @Setter
    private String TemplateCode;
    /**
     * 模板参数 设值后不用设置SmsParams
     */
    @Getter
    @Setter
    private transient Map<String, String> SmsParamsMap = Maps.newHashMap();

    @Getter
    @Setter
    private String SmsParams;

    public String toJson() {
        if (!this.SmsParamsMap.isEmpty()) {
            this.SmsParams = getGson().toJson(this.SmsParamsMap);
        }
        return getGson().toJson(this);
    }

    public AliyunSmsSendVo fromJson(String jsonStr) {
        return getGson().fromJson(jsonStr, this.getClass());
    }
}
