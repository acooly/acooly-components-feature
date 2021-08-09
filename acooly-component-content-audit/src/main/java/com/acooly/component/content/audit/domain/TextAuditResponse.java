/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:12
 */
package com.acooly.component.content.audit.domain;

import java.util.List;

import com.acooly.core.utils.Strings;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2021-07-23 21:12
 */
@Slf4j
@Data
@ToString
public class TextAuditResponse {

	private String code;
	private String msg;
	/**
	 * 检测对象对应的数据ID。 如果在检测请求参数中传入了dataId，则此处返回对应的dataId。
	 */
	private String dataId;

	/**
	 * 检测任务的ID。
	 */
	private String taskId;

	/** 原始请求内容 **/
	private String content;

	/** 禁词替换后的内容 **/
	private String filteredContent;

	/** 禁词详细解析结果 **/
	private List<TextAuditResult> results;

	/**
	 * 扩展：标记禁词文本内容（例如：赌博，海洛因）
	 */
	private String forbiddenTexts;

	// 冻结解析禁词
	public String getForbiddenTexts() {
		StringBuffer sb = new StringBuffer();
		for (TextAuditResult textAuditResult : getResults()) {
			try {
				String details = textAuditResult.getDetails();
				if (Strings.isNotBlank(details)) {
					JSONArray detailsJsons = JSON.parseArray(details);
					for (Object detailsJson : detailsJsons) {
						JSONObject contextObjects = JSON.parseObject("" + detailsJson);
						JSONArray contextJsons = contextObjects.getJSONArray("contexts");
						for (Object object : contextJsons) {
							JSONObject contextJson = JSON.parseObject("" + object);
							Object context = contextJson.get("context");
							sb.append(context);
							sb.append(",");
						}
					}
				}
				String forbiddenText = sb.toString();
				return forbiddenText.substring(0, (forbiddenText.length() - 1));
			} catch (Exception e) {
				return forbiddenTexts;
			}
		}
		return forbiddenTexts;
	}

}
