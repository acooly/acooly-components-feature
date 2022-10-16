/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-05 23:09
 */
package cn.acooly.component.rbac;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author zhangpu
 * @date 2022-10-05 23:09
 */
@Getter
@Setter
@ConfigurationProperties(RbacProperties.PREFIX)
public class RbacProperties {

    public static final String PREFIX = "acooly.rbac";
    public static final Integer USER_TYPE_ADMIN = 1;
    public static final Integer USER_TYPE_OPERATOR = 2;
    private boolean enable = true;
    /**
     * 扩展用户类型
     */
    private Map<Integer, String> userTypes = Maps.newLinkedHashMap();
    /**
     * 资源权限没有受控管理的情况下权限判断结果默认值：false
     */
    private boolean NoResourcePermitted = false;

    /**
     * 是否开启对认证信息的缓存（默认关闭），一般情况下，除非你采用外部认证，否则保持关闭
     */
    private boolean autheCachingEnabled = false;

    public RbacProperties() {
        userTypes.put(USER_TYPE_ADMIN, "管理员");
        userTypes.put(USER_TYPE_OPERATOR, "操作员");
    }
}

