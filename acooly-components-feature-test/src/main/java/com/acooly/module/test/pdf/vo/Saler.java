package com.acooly.module.test.pdf.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiaohong@acooly.cn
 * @date 2018-10-22 16:39
 */
@Getter
@Setter
public class Saler implements Serializable {
    public Saler(){}

    public Saler(String userNo){
        this.userNo = userNo;
    }
    /**
     * 购买者ID
     */
    private String userNo;
}
