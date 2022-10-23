/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by acooly
* date:2022-10-12
*/
package cn.acooly.component.rbac.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;

/**
 * 用户角色表 Entity
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
@Entity
@Table(name = "rbac_user_role")
@Getter
@Setter
public class RbacUserRole extends AbstractEntity {

    /**
     * role_id
     */
	@NotNull
    private Long roleId;

    /**
     * user_id
     */
	@NotNull
    private Long userId;

}
