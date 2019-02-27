package com.acooly.module.wechat.oauth.client.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.wechat.WechatProperties;
import com.acooly.module.wechat.oauth.client.WechatWebClientBaseService;
import com.acooly.module.wechat.oauth.client.WechatWebClientService;
import com.acooly.module.wechat.oauth.client.dto.WechatOpenIdDto;
import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信网页授权
 * 
 * @author CuiFuQ
 *
 */
@Slf4j
@Service("wechatWebClientService")
public class WechatWebClientServiceImpl implements WechatWebClientService {

	@Autowired
	private WechatProperties wechatProperties;

	@Autowired
	private WechatWebClientBaseService wechatWebClientBaseService;

	@Override
	public String getWechatOauthUrl(String redirectUri) {
		// 检查微信开关
		checkWechatEnable();
		return wechatWebClientBaseService.wechatOauth(redirectUri);
	}

	@Override
	public WechatUserInfoDto getWechatUserInfo(HttpServletRequest request, HttpServletResponse response) {
		WechatUserInfoDto wechatUserInfo = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			WechatOpenIdDto wechatOpenIdDto = wechatWebClientBaseService.getOpenId(request, response);
			if (wechatProperties.getScope().equals("snsapi_base")) {
				wechatUserInfo = new WechatUserInfoDto();
				wechatUserInfo.setOpenId(wechatOpenIdDto.getOpenId());
			} else {
				wechatUserInfo = wechatWebClientBaseService.getUserInfoByAccessToken(wechatOpenIdDto.getOpenId(),
						wechatOpenIdDto.getAccessToken());
			}
		} catch (Exception e) {
			log.error("微信页面确认授权(step 2)获取用户信息失败{}", e);
		}
		return wechatUserInfo;
	}

	/** 微信开关 */
	private void checkWechatEnable() {
		if (!wechatProperties.getEnable()) {
			throw new BusinessException("微信组件开关：已关闭");
		}
	}

}
