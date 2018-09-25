package com.acooly.core.test.domain;

import com.acooly.core.common.domain.Entityable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class City1 implements Entityable {

    @Id
    private String id;

    private String name;

    private String state;

    /**
     * 创建时间
     */
    @Column(
            name = "create_time",
            columnDefinition = " timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'"
    )
    private Date createTime = new Date();

    @Column(
            name = "update_time",
            columnDefinition =
                    "timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'"
    )
    /** 修改时间 */
    private Date updateTime = new Date();


}
