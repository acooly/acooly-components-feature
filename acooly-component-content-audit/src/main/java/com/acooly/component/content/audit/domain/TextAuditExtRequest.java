/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 16:17
 */
package com.acooly.component.content.audit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuq
 * @date 2021-08-04
 */
@Slf4j
@Data
@NoArgsConstructor
public class TextAuditExtRequest extends TextAuditRequest {

	public TextAuditExtRequest(String text) {
		this.addTask(text);
	}

}
