package com.acooly.module.obs.common.model;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

/**
 * @author shuijing
 */
@Data
public class ObjectResult extends ResultBase {

    /**
     * 返回访问的地址
     */
    private String url;

    @Override
    public String toString() {
        return new StringBuffer()
                .append("ObjectResult{")
                .append("status=[")
                .append(getStatus().code())
                .append(']')
                .append("url=[")
                .append(url)
                .append(']')
                .append("detail=[")
                .append(getDetail())
                .append(']')
                .append("code=[")
                .append(getCode())
                .append("]}")
                .toString();
    }
}
