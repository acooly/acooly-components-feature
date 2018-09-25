package com.acooly.core.test.dubbo.mock;

import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;

/**
 * @author qiubo@yiji.com
 */
public interface XXFacade {
    SingleResult<String> echo(SingleOrder<String> msg);
}
