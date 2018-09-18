package com.acooly.module.appopenapi.service;

import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.app.domain.AppBanner;
import com.acooly.module.app.service.AppBannerService;
import com.acooly.module.appopenapi.dto.MediaInfo;
import com.acooly.module.appopenapi.enums.ApiOwners;
import com.acooly.module.appopenapi.message.BannerListRequest;
import com.acooly.module.appopenapi.message.BannerListResponse;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.utils.ApiUtils;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 首页banner API
 *
 * @author zhangpu
 */
@OpenApiService(
        name = "bannerList",
        desc = "横幅广告",
        responseType = ResponseType.SYN,
        owner = ApiOwners.COMMON,
        busiType = ApiBusiType.Query
)
public class BannerListApiService extends BaseApiService<BannerListRequest, BannerListResponse> {

    @Autowired
    private AppBannerService appBannerService;
    @Autowired
    private OFileProperties oFileProperties;

    @Override
    protected void doService(BannerListRequest request, BannerListResponse response) {
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("sortTime", false);
        Map<String, Object> map = Maps.newHashMap();
        List<AppBanner> appBanners = appBannerService.query(map, sortMap);
        if (Collections3.isEmpty(appBanners)) {
            return;
        }
        for (AppBanner appBanner : appBanners) {
            response.append(convert(appBanner));
        }
    }

    private MediaInfo convert(AppBanner appBanner) {
        MediaInfo dto = new MediaInfo();
        dto.setComments(appBanner.getComments());
        dto.setImage(getFullUrl(appBanner.getMediaUrl()));
        dto.setThumbnail(dto.getImage());
        dto.setLink(getFullUrl(appBanner.getLink()));
        dto.setTitle(appBanner.getTitle());
        return dto;
    }

    private String getFullUrl(String url) {
        if (Strings.isBlank(url)) {
            return url;
        }
        if (ApiUtils.isHttpUrl(url)) {
            return url;
        }
        String fullUrl = oFileProperties.getServerRoot();
        if (!Strings.endsWith(fullUrl, "/")) {
            fullUrl = fullUrl + "/";
        }
        if (Strings.startsWith(url, "/")) {
            url = Strings.removeStart(url, "/");
        }
        fullUrl = fullUrl + url;
        return fullUrl;
    }
}
