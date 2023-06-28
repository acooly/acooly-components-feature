/*
* acooly.cn Inc.
* Copyright (c) 2023 All Rights Reserved.
* create by zhangpu
* date:2023-06-27
*/
package com.acooly.module.security.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.acooly.core.utils.ie.anno.ExportColumn;
import com.acooly.core.utils.ie.anno.ExportModel;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.domain.Sortable;

/**
 * sys_user_favorite Entity
 *
 * @author zhangpu
 * @date 2023-06-27 20:16:01
 */
@Entity
@Table(name = "sys_user_favorite")
@Getter
@Setter
@ExportModel(name = "sys_user_favorite", border = true, headerShow = true)
public class UserFavorite extends AbstractEntity implements Sortable {

    /**
     * 用户ID
     */
	@NotNull
    @ExportColumn(header = "用户ID", order = 0)
    private Long userId;

    /**
     * 资源ID
     */
	@NotNull
    @ExportColumn(header = "资源ID", order = 1)
    private Long rescId;

    /**
     * 排序值
     */
	@NotNull
    @ExportColumn(header = "排序值", order = 2)
    private Long sortTime;

}
