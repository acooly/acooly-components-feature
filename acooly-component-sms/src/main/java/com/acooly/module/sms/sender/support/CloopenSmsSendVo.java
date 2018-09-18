package com.acooly.module.sms.sender.support;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shuijing
 */
public class CloopenSmsSendVo extends BaseSmsSendVo {

    /**
     * 模板id
     */
    @Getter
    @Setter
    private String templateId;
    /**
     * 模板datas
     */
    @Getter
    @Setter
    private List datas = new ArrayList<String>();

    public String toJson() {
        return getGson().toJson(this);
    }
}
