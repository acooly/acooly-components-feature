/**
 * create by zhangpu date:2015年11月4日
 */
package com.acooly.module.app.notify.jpush.dto;

import com.acooly.core.utils.mapper.JsonMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Map;

/**
 * @author zhangpu
 * @date 2015年11月4日
 */
public class JPushMessage {

    @NotBlank
    @JsonProperty("msg_content")
    private String msgContent;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("extras")
    private Map<String, Object> extras;

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return JsonMapper.nonEmptyMapper().toJson(this);
    }
}
