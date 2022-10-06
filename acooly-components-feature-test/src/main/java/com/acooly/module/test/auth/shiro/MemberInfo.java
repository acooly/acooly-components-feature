/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-04 23:34
 */
package com.acooly.module.test.auth.shiro;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 会员认证对象，类似后台的User对象
 *
 * @author zhangpu
 * @date 2022-10-04 23:34
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberInfo {

    private String memberNo;
    private String username;

    public MemberInfo(String username, String memberNo) {
        this.memberNo = memberNo;
        this.username = username;
    }
}
