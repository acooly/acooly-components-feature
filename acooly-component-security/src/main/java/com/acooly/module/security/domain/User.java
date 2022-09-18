package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.Collections3;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author cac
 */
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"password", "roles", "salt", "hibernateLazyInitializer"})
@Getter
@Setter
public class User extends AbstractEntity {
    public static final int STATUS_ENABLE = 1;
    public static final int STATUS_LOCK = 2;
    public static final int STATUS_EXPIRES = 9;
    public static final int STATUS_DISABLE = 10;
    public static final int USER_TYPE_ADMIN = 1;
    /**
     * uid
     */
    private static final long serialVersionUID = -1740401537348774052L;
    /**
     * 登陆名
     */
    @NotBlank
    @Size(max = 32)
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    /**
     * 名字
     */
    @Size(max = 32)
    @Column(name = "real_name", length = 32)
    private String realName;

    /**
     * 姓名拼音首字母
     */
    @Size(max = 32)
    @Column(name = "pinyin", length = 32)
    private String pinyin;

    /**
     * 密码
     */
    @NotBlank
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * 密码加密填充值
     */
    @NotBlank
    @Size(max = 64)
    @Column(name = "salt", nullable = false, length = 64)
    private String salt;

    /**
     * 用户类型 1:管理员 2:操作员 3:代理商 4:客户等
     */
    @Column(name = "user_type", nullable = false)
    private int userType;

    /**
     * 电子邮件
     */
    @Size(max = 128)
    @Email
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    /**
     * 手机号码
     */
    @Size(max = 32)
    @Column(name = "mobile_no", length = 32)
    private String mobileNo;

    /**
     * 组织ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 组织名称
     */
    @Column(name = "org_name", length = 64)
    private String orgName;


    @Column(name = "last_modify_time")
    private Date lastModifyTime = new Date();

    /**
     * 密码失效时间
     */
    @Column(name = "expiration_time")
    private Date expirationTime = new Date();

    /**
     * 解鎖时间
     */
    @Column(name = "unlock_time")
    private Date unlockTime;


    /**
     * 登陆状态{1:未登陆,2:已登陆}
     */
    @Column(name = "login_status")
    private int loginStatus;

    /**
     * 登录失败次数
     */
    @Column(name = "login_fail_times")
    private int loginFailTimes;


    /**
     * 最后登陆时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 状态 {1:有效,2:过期,3:锁定,10:禁用}
     */
    @Column(name = "status")
    private int status = STATUS_ENABLE;

    /**
     * 描述
     */
    @Column(name = "descn")
    private String descn;

    /**
     * 包含的角色
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = com.acooly.module.security.domain.Role.class)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderBy(clause = "role_id")
    private Set<Role> roles;


    @Transient
    private String roleName;

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * minimal constructor
     */
    public User(String username, String password, String realName, String email) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.email = email;
    }

    /**
     * full constructor
     */
    public User(String username, String password, String realName, String email, int status, String descn, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.status = status;
        this.descn = descn;
        this.roles = roles;
    }

    @Transient
    public boolean isEnabled() {
        return (this.status == STATUS_ENABLE);
    }

    @Transient
    public String getRoleName() {
        if (Collections3.isEmpty(this.roles)) {
            return null;
        }
        List<String> roleNames = Lists.newArrayList();
        this.roles.forEach(e -> roleNames.add(e.getName()));
        return Strings.join(roleNames.iterator(), ',');
    }


    @Override
    public String toString() {
        return String.format("%s [%s]", username, realName);
    }
}
