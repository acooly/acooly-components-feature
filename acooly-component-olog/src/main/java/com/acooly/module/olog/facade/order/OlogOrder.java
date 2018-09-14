/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-10-30 15:46 创建
 */
package com.acooly.module.olog.facade.order;

import com.acooly.core.common.facade.OrderBase;
import com.acooly.module.olog.facade.dto.OlogDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author acooly
 */
@Getter
@Setter
public class OlogOrder extends OrderBase {
    private List<OlogDTO> list;
}
