package com.acooly.module.olog.facade.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author qiubo@yiji.com
 */
@Getter
@Setter
public class OlogDTO extends InfoBase {
    /**
     * 所属系统标志
     */
    @Length(max = 32)
    private String system;

    /**
     * 模块名称
     */
    @Length(max = 255)
    private String moduleName;

    /**
     * 功能模块
     */
    @NotEmpty
    @Length(max = 255)
    private String module;

    /**
     * 操作名称
     */
    @Length(max = 32)
    private String actionName;

    /**
     * 操作
     */
    @NotEmpty
    @Length(max = 32)
    private String action;

    /**
     * 操作员
     */
    @NotEmpty
    @Length(max = 64)
    private String operateUser;

    /**
     * 操作员ID
     */
    private Long operateUserId;

    /**
     * 操作结果.1:成功,2:失败
     */
    private Boolean operateResult = Boolean.TRUE;

    @Length(max = 512)
    private String operateMessage;

    /**
     * 备注
     */
    @Length(max = 255)
    private String descn;

    /**
     * 请求参数
     */
    @Length(max = 512)
    private String requestParameters;

    /**
     * 客户端信息
     */
    @Length(max = 255)
    private String clientInformations;

    /**
     * 执行时间
     */
    private long executeMilliseconds = 0;
}
