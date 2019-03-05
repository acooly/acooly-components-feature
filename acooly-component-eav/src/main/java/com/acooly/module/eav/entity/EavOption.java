/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by zhangpu
* date:2019-03-05
*/
package com.acooly.module.eav.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;

/**
 * 属性选项 Entity
 *
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Entity
@Table(name = "eav_option")
@Getter
@Setter
public class EavOption extends AbstractEntity {

	/** 父ID */
    private Long parentId;

	/** path */
	@Size(max=255)
    private String path;

	/** 编码 */
	@NotEmpty
	@Size(max=64)
    private String code;

	/** 名称 */
	@NotEmpty
	@Size(max=64)
    private String name;

	/** 排序值 */
    private Long sortTime;

	/** 状态 */
	@NotEmpty
	@Size(max=16)
    private String status;

	/** 备注 */
	@Size(max=255)
    private String memo;

}
