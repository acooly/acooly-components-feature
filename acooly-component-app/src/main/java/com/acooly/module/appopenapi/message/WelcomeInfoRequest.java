/**
 * create by zhangpu date:2015年5月6日
 */
package com.acooly.module.appopenapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.enums.DeviceType;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 欢迎信息
 *
 * @author zhangpu
 */
@Getter
@Setter
public class WelcomeInfoRequest extends ApiRequest {

    @OpenApiField(desc = "设备类型", constraint = "如果不传，则返回默认规格图片")
    private DeviceType deviceType;
}
