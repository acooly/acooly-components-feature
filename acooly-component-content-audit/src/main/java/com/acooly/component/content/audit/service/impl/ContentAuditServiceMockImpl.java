/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:34
 */
package com.acooly.component.content.audit.service.impl;

import com.acooly.component.content.audit.domain.ImageAuditRequest;
import com.acooly.component.content.audit.domain.TextAuditExtRequest;
import com.acooly.component.content.audit.domain.TextAuditExtResponse;
import com.acooly.component.content.audit.domain.TextAuditRequest;
import com.acooly.component.content.audit.service.ContentAuditService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2021-07-23 21:34
 */
@Slf4j
@NoArgsConstructor
public class ContentAuditServiceMockImpl implements ContentAuditService {

	@Override
	public void textScan(TextAuditRequest request) {

	}

	@Override
	public TextAuditExtResponse textScanExt(TextAuditExtRequest request) {
		return null;
	}

	@Override
	public void imageScan(ImageAuditRequest request) {

	}

}
