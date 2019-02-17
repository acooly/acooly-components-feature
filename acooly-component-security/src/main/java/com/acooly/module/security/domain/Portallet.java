package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.security.config.FrameworkProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 桌面管理 Entity
 *
 * <p>Date: 2013-05-02 15:40:56
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "SYS_PORTALLET")
@Getter
@Setter
public class Portallet extends AbstractEntity {
    private static final long serialVersionUID = 7760426571421698795L;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 64)
    private String title;

    /**
     * 高度
     */
    @Column(name = "width", nullable = false, length = 4)
    private int width = 400;

    /**
     * 宽度
     */
    @Column(name = "height", nullable = false, length = 4)
    private int height = 200;

    /**
     * 可否收缩
     */
    @Column(name = "collapsible", nullable = false, length = 4)
    private int collapsible;

    /**
     * 内容类型 1:url,2:html
     */
    @Column(name = "load_mode", nullable = false, length = 4)
    private int loadMode = FrameworkProperties.LOAD_MODE_URL;

    /**
     * 加载方式 1:ajax,2:iframe
     */
    @Column(name = "show_mode", nullable = false, length = 4)
    private int showMode = FrameworkProperties.SHOW_MODE_AJAXLOAD;

    /**
     * 连接地址
     */
    @Column(name = "href", nullable = true, length = 128)
    private String href;

    /**
     * HTML内容
     */
    @Column(name = "content", nullable = true)
    private String content;

    /**
     * 所属用户
     */
    @Column(name = "userName", nullable = true, length = 32)
    private String userName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
