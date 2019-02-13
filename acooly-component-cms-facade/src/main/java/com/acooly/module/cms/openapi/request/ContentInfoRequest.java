package com.acooly.module.cms.openapi.request;


import com.acooly.module.cms.enums.ContentQueryTypeEnum;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询单条内容信息 请求报文
 *
 * @author zhangpu
 * @note 支持三种方式查询单条记录。
 * <li>id: 根据内容主键查询，一般在列表查询后，根据列表返回的每条数据的id进行反查</li>
 * <li>key: 根据每条内容后台定义的唯一编码keycode查询</li>
 * <li>type: 根据内容的类型的编码查询最新的一条数据</li>
 */
@Data
public class ContentInfoRequest extends ApiRequest {

    @NotNull
    @OpenApiField(desc = "查询方式")
    private ContentQueryTypeEnum contentQueryType = ContentQueryTypeEnum.id;

    @NotBlank
    @OpenApiField(desc = "查询值", constraint = "根据查询方式不同传入不同值，如果是id，请转换为字符串方式传入")
    private String key;

}
