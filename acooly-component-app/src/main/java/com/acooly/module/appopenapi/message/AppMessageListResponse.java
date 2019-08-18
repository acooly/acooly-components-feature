/**
 * create by zhangpu date:2016年2月13日
 */
package com.acooly.module.appopenapi.message;

import com.acooly.module.appopenapi.dto.AppMessageDto;
import com.acooly.openapi.framework.common.message.PageApiResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu
 * @date 2016年2月13日
 */
@Getter
@Setter
public class AppMessageListResponse extends PageApiResponse<AppMessageDto> {
}
