/**
 * create by zhangpu date:2015年5月10日
 */
package com.acooly.module.appopenapi.message;

import com.acooly.module.appopenapi.dto.MediaInfo;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页 banner 列表
 *
 * @author zhangpu
 */
@Getter
@Setter
public class BannerListResponse extends ApiResponse {

    @OpenApiField(desc = "媒体列表", ordinal = 1)
    private List<MediaInfo> banners = Lists.newArrayList();

    public void append(MediaInfo dto) {
        banners.add(dto);
    }
}
