/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.module.app;

import static com.acooly.module.app.AppProperties.PREFIX;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author qiubo@yiji.com
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class AppProperties {
	public static final String PREFIX = "acooly.app";
	private Boolean enable = true;
	private String storagePath = "app";

	private JPush jpush = new JPush();

	@Data
	public static class JPush {
		private boolean enable = true;
		private String gateway = "https://api.jpush.cn/v3/push";
		private String appKey;
		private String masterSecret;
		/**
		 * 离线消息保留时间,单位秒，默认1天(86400)，0不保存，最大10天
		 */
		private int timeToLive = 86400;

		private boolean apns = false;

		// 用户标签(需要购买极光的vip功能)---详情查阅readme.md
		private Map<String, String> tags;

		// 苹果手机跳转（极光扩展参数：iosJump）---详情查阅readme.md
		private Map<String, String> iosJump;

		// 安卓手机跳转（极光扩展参数：androidJump）---详情查阅readme.md
		private Map<String, String> androidJump;

	}
}
