/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:12
 */
package com.acooly.component.content.audit.domain;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.acooly.component.content.audit.enums.ContentAuditError;

/**
 * @author cuifuq
 * @date 2021-08-04
 */
@Slf4j
@Data
@ToString
public class TextAuditExtResponse {

	private ContentAuditError auditResult = ContentAuditError.AUDIT_OK;

	
	/*
	 * 审核结果 != AUDIT_OK;此字段 有效
	 */
	private List<TextAuditResponse> forbiddenContents;

}
