/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-10-05
*/
package cn.acooly.component.rbac.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;
import com.acooly.core.utils.enums.AbleStatus;

/**
 * 组织机构 Entity
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Entity
@Table(name = "rbac_org")
@Getter
@Setter
public class RbacOrg extends AbstractEntity {

    /**
     * 父类id
     */
	@NotNull
    private Long parentId;

    /**
     * 机构编码
     */
	@Size(max = 64)
    private String code;

    /**
     * 机构名称
     */
	@Size(max = 32)
    private String name;

    /**
     * 搜索路径
     */
	@NotBlank
	@Size(max = 128)
    private String path;

    /**
     * 排序值
     */
	@NotNull
    private Long orderTime;

    /**
     * 省
     */
	@Size(max = 64)
    private String province;

    /**
     * 市
     */
	@Size(max = 64)
    private String city;

    /**
     * 区/县
     */
	@Size(max = 64)
    private String district;

    /**
     * 手机号码
     */
	@Size(max = 20)
    private String mobileNo;

    /**
     * 地址
     */
	@Size(max = 255)
    private String address;

    /**
     * 联系人
     */
	@Size(max = 64)
    private String contacts;

    /**
     * 固定电话
     */
	@Size(max = 20)
    private String telephone;

    /**
     * 邮件
     */
	@Size(max = 64)
    private String email;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
	@NotNull
    private AbleStatus status;

    /**
     * 备注
     */
	@Size(max = 255)
    private String memo;

}
