/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-08-31
*/
package com.acooly.module.chat.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;

/**
 * IM聊天-客服信息模板 Entity
 *
 * @author acooly
 * Date: 2018-08-31 18:27:45
 */
@Entity
@Table(name = "im_chat_info_template")
@Getter
@Setter
public class ChatInfoTemplate extends AbstractEntity {

	/** 标题 */
	@NotEmpty
	@Size(max=64)
    private String title;

	/** 内容 */
	@NotEmpty
	@Size(max=5000)
    private String text;

	/** 备注 */
	@Size(max=255)
    private String comments;

}
