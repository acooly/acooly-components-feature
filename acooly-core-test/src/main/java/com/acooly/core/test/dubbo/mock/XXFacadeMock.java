package com.acooly.core.test.dubbo.mock;

import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;
import org.springframework.stereotype.Service;

/**
 * @author qiubo@yiji.com
 */
@Service
public class XXFacadeMock implements XXFacade {
    @Override
    public SingleResult<String> echo(SingleOrder<String> msg) {
        return SingleResult.from("mocked");
    }
}
