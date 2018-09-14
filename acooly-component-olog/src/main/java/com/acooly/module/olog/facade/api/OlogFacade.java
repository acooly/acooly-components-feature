/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-10-30 16:04 创建
 */
package com.acooly.module.olog.facade.api;

import com.acooly.module.olog.facade.order.OlogOrder;
import com.acooly.module.olog.facade.result.OlogResult;

/**
 * @author acooly
 */
public interface OlogFacade {
    OlogResult logger(OlogOrder order);
}
