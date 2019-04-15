package com.acooly.module.wechat.oauth.mini.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.wechat.WechatProperties;
import com.acooly.module.wechat.oauth.mini.WechatMiniClientService;
import com.acooly.module.wechat.oauth.mini.dto.WechatMiniSession;
import com.acooly.module.wechat.oauth.mini.enums.WechatMiniClientEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序授权
 * 
 * @author CuiFuQ
 *
 */
@Slf4j
@Service("wechatMiniClientService")
public class WechatMiniClientServiceImpl implements WechatMiniClientService {

	@Autowired
	private WechatProperties wechatProperties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	private final String WECHAT_MINI_ACCESS_TOKEN = "wechat_mini_access_token";

	@Autowired
	private OFileProperties oFileProperties;

	@Override
	public WechatMiniSession loginAuthVerify(String jsCode) {
		String appId = wechatProperties.getMiniClient().getAppid();
		String secret = wechatProperties.getMiniClient().getSecret();

		String openidUrl = wechatProperties.getMiniClient().getApiUrl()
				+ WechatMiniClientEnum.sns_jscode2session.code();
		Map<String, Object> requestData = Maps.newTreeMap();
		requestData.put("appid", appId);
		requestData.put("secret", secret);
		requestData.put("js_code", jsCode);
		requestData.put("grant_type", "authorization_code");
		String requestUrl = HttpRequest.append(openidUrl, requestData);
		log.info("微信小程序[登录凭证校验],请求地址:{}", requestUrl);

		HttpRequest httpRequest = HttpRequest.get(requestUrl).acceptCharset(HttpRequest.CHARSET_UTF8);
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();
		int httpCode = httpRequest.code();
		String httpResponse = httpRequest.body(HttpRequest.CHARSET_UTF8);

		JSONObject bodyJson = JSON.parseObject(httpResponse);
		log.info("微信小程序[登录凭证校验],响应数据:{}", httpResponse);

		if (httpCode != 200) {
			log.info("微信小程序[登录凭证校验]失败" + bodyJson);
			throw new BusinessException(httpResponse);
		}

		String errMsg = bodyJson.getString("errmsg");
		if (StringUtils.isNotBlank(errMsg)) {
			log.error("微信小程序[登录凭证校验]失败：{}", bodyJson);
			throw new BusinessException(bodyJson.getString("errmsg"), bodyJson.getString("errcode"));
		}

		// wechatMiniSession 微信用户信息
		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		WechatMiniSession dto = jsonMapper.fromJson(httpResponse, WechatMiniSession.class);
		return dto;
	}

	public String getAccessToken() {
		String accessToken = (String) redisTemplate.opsForValue().get(WECHAT_MINI_ACCESS_TOKEN);

		// 重新获取 accessToken
		if (StringUtils.isBlank(accessToken)) {
			String openidUrl = wechatProperties.getMiniClient().getApiUrl() + WechatMiniClientEnum.cgi_bin_token.code();
			Map<String, Object> requestData = Maps.newTreeMap();
			requestData.put("appid", wechatProperties.getMiniClient().getAppid());
			requestData.put("secret", wechatProperties.getMiniClient().getSecret());
			requestData.put("grant_type", "client_credential");

			String requestUrl = HttpRequest.append(openidUrl, requestData);

			log.info("微信小程序[获取access_token],请求地址:{}", requestUrl);
			HttpRequest httpRequest = HttpRequest.get(requestUrl).acceptCharset(HttpRequest.CHARSET_UTF8);
			httpRequest.trustAllCerts();
			httpRequest.trustAllHosts();
			int httpCode = httpRequest.code();
			String resultBody = httpRequest.body(HttpRequest.CHARSET_UTF8);
			log.info("微信小程序[获取access_token],响应数据:{}", resultBody);

			JSONObject bodyJson = JSON.parseObject(resultBody);
			if (httpCode != 200) {
				log.info("微信小程序获取accessToken失败，" + bodyJson.get("errmsg"));
				throw new BusinessException(bodyJson.getString("errmsg"), bodyJson.getString("errcode"));
			}
			log.info("微信小程序重新获取access_token数据{}", bodyJson);
			accessToken = setRedisAccessToken(bodyJson);
		}
		return accessToken;
	}

	/**
	 * 设置redis，时间过期：微信有效时间-15分钟 ，重新获取
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	private String setRedisAccessToken(JSONObject obj) {
		log.info("redis设置微信小程序 access_token：{}", obj);
		String accessToken = obj.getString("access_token");
		Long expiresIn = obj.getLong("expires_in");
		redisTemplate.opsForValue().set(WECHAT_MINI_ACCESS_TOKEN, accessToken, (expiresIn - 900), TimeUnit.SECONDS);
		return accessToken;
	}

	public String getMiniProgramImgCode(String scene, String page) {

		String imgCodeUrl = null;

		if (Strings.isBlank(scene)) {
			throw new BusinessException("scene不能为空");
		}

		String accessToken = getAccessToken();
		String openidUrl = wechatProperties.getMiniClient().getApiUrl()
				+ WechatMiniClientEnum.wxa_getwxacodeunlimit.code();
		Map<String, Object> requestData = Maps.newTreeMap();
		requestData.put("access_token", accessToken);

		String requestUrl = HttpRequest.append(openidUrl, requestData);

		JSONObject paramJson = new JSONObject();
		paramJson.put("scene", scene);
		paramJson.put("page", page);

		log.info("微信小程序[获取小程序码],请求地址:{},请求数据：{}", requestUrl, paramJson);

		HttpRequest httpRequest = HttpRequest.post(requestUrl).acceptCharset(HttpRequest.CHARSET_UTF8)
				.send(paramJson.toString());
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();

		int httpCode = httpRequest.code();
		if (httpCode != 200) {
			log.info("微信小程序[获取小程序码]失败");
			throw new BusinessException("微信小程序[获取小程序码]失败");
		}

		byte[] bodyByte = httpRequest.bytes();

		ByteArrayInputStream inputStream = null;
		FileOutputStream outputStream = null;

		log.info("微信小程序[获取小程序码],响应报文长度：{}", bodyByte.length);

		try {
			inputStream = new ByteArrayInputStream(bodyByte);

			StringBuffer storageRoot = new StringBuffer();
			storageRoot.append(oFileProperties.getStorageRoot());
			storageRoot.append("wechat" + File.separator + "miniLogo");
			storageRoot.append(File.separator);
			storageRoot.append(dateFilePath());
			storageRoot.append(File.separator);

			File file = new File(storageRoot.toString());
			File files = new File(storageRoot.toString(), Ids.oid() + ".png");

			String fileAbsolutePath = files.getAbsolutePath();

			// \var\log\webapps\nfs\wechat\miniLogo\2019\03\31\o19033109521185600007.png
			log.info("微信小程序[获取小程序码]本地存储路径：{}", fileAbsolutePath);

			if (!file.exists()) {
				file.mkdirs();
			}

			if (!files.exists()) {
				files.createNewFile();
			}

			outputStream = new FileOutputStream(files);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();

			String serverRoot = oFileProperties.getServerRoot();

			String filePath = fileAbsolutePath.replace(oFileProperties.getStorageRoot(), "");
			if (!Strings.startsWith(filePath, "/")) {
				filePath = "/" + filePath;
			}
			imgCodeUrl = serverRoot + filePath;
			log.info("微信小程序[获取小程序码]浏览器访问地址：{}", imgCodeUrl);

		} catch (Exception e) {
			log.error("微信小程序[获取小程序码]本地存储失败{}", e);
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				log.error("微信小程序[获取小程序码]失败{}", e);
			}
		}

		return imgCodeUrl;
	}

	/**
	 * 时间的文件路径
	 * 
	 * @return
	 */
	private String dateFilePath() {
		String pathTimestamp = Dates.format(new Date(), "yyyyMMddHHmmssSSS");
		String yearPart = StringUtils.left(pathTimestamp, 4);
		String monthPart = StringUtils.substring(pathTimestamp, 4, 6);
		String dayPart = StringUtils.substring(pathTimestamp, 6, 8);
		String subPath = File.separator + yearPart + File.separator + monthPart + File.separator + dayPart;
		return subPath;
	}

}
