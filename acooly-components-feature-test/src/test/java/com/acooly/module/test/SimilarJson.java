/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-29 16:52
 */
package com.acooly.module.test;

import com.acooly.core.utils.Strings;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-04-29 16:52
 */
@Slf4j
public class SimilarJson {

    public static void main(String[] args) {
        String similarJson = "{title:性别,type:select,options:{1:男,2:女,3:人妖}}";
        similarJson = Strings.replace(similarJson, "’", "'");
        similarJson = Strings.replace(similarJson, "‘", "'");
        log.info("similarJson:{}", similarJson);
        JSONObject jsonObject = JSON.parseObject(similarJson);
        log.info("jsonObject:{}", jsonObject);
    }
}
