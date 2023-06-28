/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-06-27 20:30
 */
package com.acooly.module.security.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu
 * @date 2023-06-27 20:30
 */
@Getter
@Setter
public class UserFavorites extends InfoBase {

    private Long id;

    private Long userId;

    private Long rescId;

    private Long sortTime;

    private String rescName;

    private String rescValue;

    private String rescIcon;

    private int showMode;
}
