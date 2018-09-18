package com.acooly.module.cms.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.Set;

/**
 * 内容类型 Entity
 *
 * <p>Date: 2013-07-12 15:06:45
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "CMS_CONTENT_TYPE")
@JsonIgnoreProperties({"parent", "handler", "hibernateLazyInitializer"})
public class ContentType extends AbstractEntity {

    private static final long serialVersionUID = -2050961554185236572L;
    /**
     * 搜索路径
     */
    private String path;
    /**
     * 类型编码 (4位业务编码,如：1000)
     */
    private String code;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 备注
     */
    private String comments;

    /**
     * 内容类型父节点
     */
    @ManyToOne
    @JoinColumn(name = "PARENTID")
    @LazyToOne(value = LazyToOneOption.PROXY)
    private ContentType parent;
    /**
     * 内容类型子集
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @OrderBy(clause = "id")
    private Set<ContentType> children;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ContentType getParent() {
        return parent;
    }

    public void setParent(ContentType parent) {
        this.parent = parent;
    }

    public Set<ContentType> getChildren() {
        return children;
    }

    public void setChildren(Set<ContentType> children) {
        this.children = children;
    }
}
