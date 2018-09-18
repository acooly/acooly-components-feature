/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by qiubo
* date:2018-06-26
*/
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * eav_schema Entity
 *
 * @author qiubo
 * Date: 2018-06-26 21:51:37
 */
@Entity
@Table(name = "eav_schema")
public class EavSchema extends AbstractEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;


	/** 名称 */
	@NotEmpty
	@Size(max=128)
    private String name;

	/** 备注 */
	@Size(max=128)
    private String memo;



	


	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getMemo(){
		return this.memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}



}
