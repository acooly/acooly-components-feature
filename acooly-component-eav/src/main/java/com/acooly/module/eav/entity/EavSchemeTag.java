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
 * 方案标签 Entity
 *
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Entity
@Table(name = "eav_scheme_tag")
@Getter
@Setter
public class EavSchemeTag extends AbstractEntity {

	/** 方案ID */
	@NotNull
    private Long schemeid;

	/** 标签 */
	@NotEmpty
	@Size(max=64)
    private String tag;

	/** 备注 */
	@Size(max=255)
    private String memo;

}
