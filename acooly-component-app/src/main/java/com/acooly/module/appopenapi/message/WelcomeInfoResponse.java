package com.acooly.module.appopenapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 欢迎信息 响应报文
 *
 * @author zhangpu
 * @note 包括欢迎页面logo上的广告图和一组轮播欢迎导航图片
 */
@Getter
@Setter
public class WelcomeInfoResponse extends ApiResponse {

    @OpenApiField(desc = "启动界面", constraint = "全URL，可直接访问. 返回空着不显示")
    private String welcome;

    @OpenApiField(desc = "导航图组", constraint = "全URL，可直接访问. 返回空着不显示")
    private List<String> guides = Lists.newArrayList();

    public void append(String url) {
        guides.add(url);
    }
}
