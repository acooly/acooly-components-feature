package com.acooly.module.obs.client.oss.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 上传object操作的返回结果。
 */
@Getter
@Setter
public class PutObjectResult extends GenericResult {

    // Object的ETag值。
    private String eTag;

    private String buketName;
}
