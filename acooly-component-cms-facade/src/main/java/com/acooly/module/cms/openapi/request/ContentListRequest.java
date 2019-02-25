package com.acooly.module.cms.openapi.request;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.PageApiRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2019-01-02 01:28
 */
@Data
public class ContentListRequest extends PageApiRequest {

    @OpenApiField(desc = "内容类型编码", constraint = "标记类型模块,获取该模块最新内容，如：aboutus，具体类型根据后台BOSS配置为准")
    private String typeCode;

    @OpenApiField(desc = "搜索关键字", constraint = "关键字,如：活动")
    private String searchKey;

}
