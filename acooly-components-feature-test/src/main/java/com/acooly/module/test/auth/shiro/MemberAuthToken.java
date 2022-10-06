/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-04 23:10
 */
package com.acooly.module.test.auth.shiro;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhangpu
 * @date 2022-10-04 23:10
 */
@Slf4j
@NoArgsConstructor
public class MemberAuthToken implements AuthenticationToken {

    private MemberInfo memberInfo;


    public MemberAuthToken(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public MemberAuthToken(String username, String memberNo) {
        this.memberInfo = new MemberInfo(username, memberNo);
    }

    @Override
    public Object getPrincipal() {
        return this.memberInfo;
    }

    /**
     * 密码/安全码等
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return "";
    }
}
