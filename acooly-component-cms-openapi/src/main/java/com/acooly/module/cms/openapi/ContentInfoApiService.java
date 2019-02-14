package com.acooly.module.cms.openapi;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.module.cms.CmsProperties;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.dto.ContentInfo;
import com.acooly.module.cms.enums.ContentQueryTypeEnum;
import com.acooly.module.cms.openapi.request.ContentInfoRequest;
import com.acooly.module.cms.openapi.response.ContentInfoResponse;
import com.acooly.module.cms.service.ContentService;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.exception.ApiServiceException;
import com.acooly.openapi.framework.core.service.base.AbstractApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * 内容信息 API
 *
 * @author zhangpu
 * @note <p>
 * 通过id,编码或模块名称（返回该模块对应的最新一条数据）三种方式获取内容详情。
 * </p>
 */
@OpenApiService(name = "contentInfo", desc = "内容信息", responseType = ResponseType.SYN, busiType = ApiBusiType.Query)
public class ContentInfoApiService extends AbstractApiService<ContentInfoRequest, ContentInfoResponse> {

    @Autowired
    private ContentService contentService;
    @Autowired
    private RedisTemplate<String, ContentInfoResponse> redisTemplate;
    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private CmsProperties cmsProperties;

    @Override
    protected void doService(ContentInfoRequest request, ContentInfoResponse response) {
        Content content = null;
        if (null == request.getContentQueryType()) {
            throw new BusinessException("contentQueryType不能为空", "PARAMETER_ERROR");
        }

        if (cmsProperties.getCacheTimeout() > 0) {
            ContentInfoResponse cache = getCached(request);
            if (cache != null) {
                response.setContentInfo(cache.getContentInfo());
                return;
            }
        }

        try {
            if (request.getContentQueryType().equals(ContentQueryTypeEnum.id)) {
                content = contentService.getByIdNoLazy(Long.valueOf(request.getKey()));
            } else if (request.getContentQueryType().equals(ContentQueryTypeEnum.type)) {
                content = contentService.getLatestByTypeCodeNoLazy(request.getKey());
            } else {
                content = contentService.getByKeycodeNoLazy(request.getKey());
            }
            if (content != null) {
                copyResponse(request, response, content);
            } else {
                throw new ApiServiceException(ApiServiceResultCode.OBJECT_NOT_EXIST);
            }
        } catch (ApiServiceException ae) {
            throw ae;
        } catch (BusinessException be) {
            throw new ApiServiceException(be.getCode(), be.getMessage());
        } catch (Exception e) {
            throw new ApiServiceException(ApiServiceResultCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    protected ContentInfoResponse getCached(ContentInfoRequest request) {
        return redisTemplate.opsForValue().get(getCacheKey(request));
    }

    protected String getCacheKey(ContentInfoRequest request) {
        String key = null;
        if (request.getContentQueryType().equals(ContentQueryTypeEnum.id)) {
            key = "cms.id1." + request.getKey();
        } else if (request.getContentQueryType().equals(ContentQueryTypeEnum.type)) {
            key = "cms.type1." + request.getKey();
        } else {
            key = "cms.other1." + request.getKey();
        }
        return key;
    }

    private void copyResponse(ContentInfoRequest request, ContentInfoResponse response, Content content) {
        ContentInfo contentInfo = new ContentInfo();
        contentInfo.setId(content.getId());
        contentInfo.setCode(content.getKeycode());
        contentInfo.setTitle(content.getTitle());
        if (StringUtils.isNotEmpty(content.getCover())) {
            contentInfo.setCover(oFileProperties.getAccessableUrl(content.getCover()));
        } else {
            contentInfo.setCover("");
        }
        contentInfo.setPubTime(Dates.format(content.getPubDate()));
        contentInfo.setSubject(content.getSubject());
        contentInfo.setContent(content.getContentBody().getBody());
        contentInfo.setTypeCode(content.getContentType().getCode());
        contentInfo.setTypeName(content.getContentType().getName());
        contentInfo.setWebTitle(content.getWebTitle());
        contentInfo.setKeywords(content.getKeywords());
        contentInfo.setDescription(content.getSubject());
        response.setContentInfo(contentInfo);
        if (cmsProperties.getCacheTimeout() > 0) {
            redisTemplate.opsForValue().set(getCacheKey(request), response, cmsProperties.getCacheTimeout(), TimeUnit.SECONDS);
        }

    }
}
