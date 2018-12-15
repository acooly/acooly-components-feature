/*
 * acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * create by acooly
 * date:2016-12-19
 *
 */
package com.acooly.core.test.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.Money;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * app Entity
 *
 * @author acooly Date: 2016-12-19 21:09:09
 */
@Entity
@Table(name = "app")
@Getter
@Setter
public class App extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * display_name
     */
    private String displayName;
    /**
     * name
     */
    private String name;
    /**
     * parent_app_id
     */
    private Long parentAppId;
    /**
     * type
     */
    private String type;
    /**
     * user_id
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date rawAddTime = new Date();
    /**
     * 修改时间
     */
    private Date rawUpdateTime = new Date();
    /**
     * parent_id
     */
    private Long parentId;
    private Money price;
}
