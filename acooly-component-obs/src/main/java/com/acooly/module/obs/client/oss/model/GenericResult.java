package com.acooly.module.obs.client.oss.model;

import com.acooly.module.obs.client.oss.ResponseMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * A generic result that contains some basic response options, such as requestId.
 *
 * @author shuijing
 */
@Getter
@Setter
public abstract class GenericResult {

    ResponseMessage response = new ResponseMessage();
    private String requestId;
    private Long clientCRC;
    private Long serverCRC;
}
