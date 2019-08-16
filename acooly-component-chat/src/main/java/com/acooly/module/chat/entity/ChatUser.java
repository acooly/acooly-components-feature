/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-30
 */
package com.acooly.module.chat.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.chat.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * IM聊天-用户名 Entity
 *
 * @author acooly Date: 2018-08-30 17:57:10
 */
@Entity
@Table(name = "im_chat_user")
@Getter
@Setter
public class ChatUser extends AbstractEntity {

    /**
     * 男
     **/
    public static int man = 1;
    /**
     * 女
     **/
    public static int woman = 2;

    /**
     * 用户名
     */
    @NotBlank
    @Size(max = 64)
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    @Size(max = 64)
    private String password;

    /**
     * 昵称
     */
    @NotBlank
    @Size(max = 64)
    private String nickName;

    /**
     * 个性签名
     */
    @Size(max = 128)
    private String signature;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    private TypeEnum type = TypeEnum.admin;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private AbleStatus status = AbleStatus.enable;

    /**
     * 客服指定消息模板id
     */
    private Long infoTempId;

    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;

}
