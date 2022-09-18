package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 系统角色
 *
 * @author zhangpu
 */
@Entity
@Table(name = "sys_role")
@JsonIgnoreProperties(value = "users", ignoreUnknown = true)
@Getter
@Setter
public class Role extends AbstractEntity {

    /**
     * UID
     */
    private static final long serialVersionUID = 7250772367888874004L;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false, length = 64)
    @NotBlank
    @Size(max = 64)
    private String name;

    /**
     * 所述组织ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 描述
     */
    @NotBlank
    @Size(max = 255)
    @Column(name = "descn", nullable = false, length = 255)
    private String descn;

    /**
     * 包含的用户
     */
    @ManyToMany(mappedBy = "roles", targetEntity = User.class, cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    private Set<User> users;

    /**
     * 包含的资源
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = com.acooly.module.security.domain.Resource.class)
    @JoinTable(
            name = "sys_role_resc",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = @JoinColumn(name = "resc_id")
    )
    @OrderBy(clause = "resc_id")
    private Set<Resource> rescs;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    /**
     * full constructor
     */
    public Role(String name, String descn, Set<User> users, Set<Resource> rescs) {
        this.name = name;
        this.descn = descn;
        this.users = users;
        this.rescs = rescs;
    }

    @Override
    public String toString() {
        return String.format("Role: {name:%s, descn:%s}", name, descn);
    }
}
