package com.acooly.core.test.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-25 17:18
 */
@Slf4j
@Data
public class DeveloperVo implements Serializable {

    private String name;

    private String mail;

    private String lastName;
}
