package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.security.config.FrameworkProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "sys_resource")
@JsonIgnoreProperties(value = {"roles", "handler", "parent"}, ignoreUnknown = true)
@Getter
@Setter
public class Resource extends AbstractEntity {
    /**
     * uid
     */
    private static final long serialVersionUID = -8335507697312033450L;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false, length = 16)
    private String type = FrameworkProperties.RESOURCE_TYPE_URL;

    /**
     * 排序时间(默认倒序)
     */
    @Column(name = "order_time", nullable = false)
    private Date orderTime = new Date();

    /**
     * 资源串
     */
    @Column(name = "value", nullable = true, length = 255)
    private String value;

    /**
     * 图标
     */
    @Column(name = "icon", length = 64)
    private String icon;

    /**
     * 描述
     */
    @Column(name = "descn", nullable = true, length = 255)
    private String descn;


    /**
     * 是否显示(0:显示,1:隐藏)
     */
    @Column(name = "show_state", nullable = false)
    private int showState = FrameworkProperties.SHOW_STATE_YES;

    /**
     * 显示方式(用于easyUI或EXTJS,1:AJAXLOAD,2:IFRAME)
     */
    @Column(name = "show_mode", nullable = false)
    private int showMode = FrameworkProperties.SHOW_MODE_AJAXLOAD;

    /**
     * 包含角色
     */
    @ManyToMany(mappedBy = "rescs", targetEntity = com.acooly.module.security.domain.Role.class)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @OrderBy(clause = "role_id")
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "parentid")
    @LazyToOne(value = LazyToOneOption.PROXY)
    private Resource parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy(clause = "order_time desc")
    private Set<Resource> children;

    @Override
    public String toString() {
        return String.format("Resource: {name:%s, resType:%s, resString:%s, descn:%s}", name, type, value, descn);
    }
}
