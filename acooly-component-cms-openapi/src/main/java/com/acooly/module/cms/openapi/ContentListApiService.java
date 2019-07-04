package com.acooly.module.cms.openapi;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.cms.CmsApiDocType;
import com.acooly.module.cms.CmsProperties;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.dto.ContentListInfo;
import com.acooly.module.cms.openapi.request.ContentListRequest;
import com.acooly.module.cms.openapi.response.ContentListResponse;
import com.acooly.module.cms.service.ContentService;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.openapi.framework.common.annotation.ApiDocNote;
import com.acooly.openapi.framework.common.annotation.ApiDocType;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.exception.ApiServiceException;
import com.acooly.openapi.framework.core.service.base.AbstractApiService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 内容列表 API
 *
 * @author zhangpu
 * @note <p>
 * 按类别查询文章类内容分页列表。
 * </p>
 */
@Slf4j
@ApiDocType(code = CmsApiDocType.CODE, name = CmsApiDocType.NAME)
@ApiDocNote("CMS系统内容列表查询(分页),支持传入分类编码和关键字（关键字模匹配标题，主题和内容）。")
@OpenApiService(name = "contentList", desc = "内容列表", responseType = ResponseType.SYN, busiType = ApiBusiType.Query)
public class ContentListApiService extends AbstractApiService<ContentListRequest, ContentListResponse> {

    @Autowired
    private ContentService contentService;
    @Autowired
    private RedisTemplate<String, ContentListResponse> redisTemplate;
    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private CmsProperties cmsProperties;

    @Override
    protected void doService(ContentListRequest request, ContentListResponse response) {

        try {
            String key = getCachedKey(request);
            if (cmsProperties.getCacheTimeout() > 0) {
                ContentListResponse cached = redisTemplate.opsForValue().get(key);
                if (cached != null) {
                    response.setRows(cached.getRows());
                    response.setTotalPages(cached.getTotalPages());
                    response.setTotalRows(cached.getTotalRows());
                    return;
                }
            }
            PageInfo<Content> pageInfo = new PageInfo<Content>(request.getLimit(), request.getStart());
            Map<String, Object> map = Maps.newHashMap();
            map.put("EQ_status", Content.STATUS_ENABLED);
            String typeCode = request.getTypeCode();
            if (Strings.isNoneBlank(typeCode)) {
                map.put("EQ_contentType.code", typeCode);
            }
            String searchKey = request.getSearchKey();
            if (Strings.isNotBlank(searchKey)) {
                map.put("LIKE_keywords", searchKey);
            }

            Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
            sortMap.put("pubDate", true);
            contentService.query(pageInfo, map, sortMap);
            if (pageInfo != null) {
                if (!Collections3.isEmpty(pageInfo.getPageResults())) {
                    List<ContentListInfo> cli = Lists.newArrayList();
                    for (Content content : pageInfo.getPageResults()) {
                        cli.add(convert(content));
                    }
                    response.setRows(cli);
                }
                response.setTotalPages(pageInfo.getTotalPage());
                response.setTotalRows(pageInfo.getTotalCount());
                if (cmsProperties.getCacheTimeout() > 0) {
                    redisTemplate.opsForValue().set(key, response, cmsProperties.getCacheTimeout(), TimeUnit.SECONDS);
                }

            }

        } catch (BusinessException be) {
            throw new ApiServiceException(be.getCode(), be.getMessage());
        } catch (Exception e) {
            throw new ApiServiceException(ApiServiceResultCode.INTERNAL_ERROR, e.getMessage());
        }

    }


    private String getCachedKey(ContentListRequest request) {
        String key = "cms.contentList.l" + request.getLimit() + ".s" + request.getStart();
        if (Strings.isNotBlank(request.getTypeCode())) {
            key = key + ".t" + request.getTypeCode();
        }
        if (Strings.isNotBlank(request.getSearchKey())) {
            key = key + ".k" + request.getSearchKey();
        }
        return key;
    }

    private ContentListInfo convert(Content content) {
        ContentListInfo c = new ContentListInfo();
        c.setId(content.getId());
        c.setCode(content.getKeycode());
        c.setPubDate(content.getPubDate());
        c.setTitle(content.getTitle());
        c.setSubject(content.getSubject());
        if (StringUtils.isNotEmpty(content.getCover())) {
            c.setCover(oFileProperties.getAccessableUrl(content.getCover()));
        } else {
            c.setCover("");
        }
        c.setLink(content.getLink());
        return c;
    }
}
