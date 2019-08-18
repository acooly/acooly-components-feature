/**
 * create by zhangpu date:2016年2月13日
 */
package com.acooly.module.appopenapi.dto;

import com.acooly.module.app.enums.AppMessageType;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhangpu
 * @date 2016年2月13日
 */
public class AppMessageDto {

    /**
     * 主键ID
     */
    @NotNull
    @OpenApiField(desc = "唯一标识", demo = "1212", ordinal = 1)
    private Long id;
    /**
     * 标题
     */
    @NotEmpty
    @OpenApiField(desc = "标题", demo = "业务通知", ordinal = 2)
    private String title;
    /**
     * 发送时间
     */
    @NotNull
    @OpenApiField(desc = "发送时间", demo = "2016-11-12 12:12:12", ordinal = 3)
    private Date sendTime;
    /**
     * 群发类型
     */
    @NotNull
    @OpenApiField(desc = "群发类型", ordinal = 4)
    private AppMessageType type;
    /**
     * 发送内容
     */
    @NotEmpty
    @OpenApiField(desc = "内容", constraint = "通知push的文字内容,可以是HTML", demo = "订单提交成功.", ordinal = 5)
    private String content;
    /**
     * 会话内容
     */
    @OpenApiField(desc = "会话", constraint = "这里是附带的自定义协议内容。",
            demo = "{\"busiType\":\"order\",\"busiId\",112}", ordinal = 6)
    private String context;
    /**
     * 发送人
     */
    @OpenApiField(desc = "发送人", demo = "system", ordinal = 7)
    private String sender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public AppMessageType getType() {
        return type;
    }

    public void setType(AppMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
