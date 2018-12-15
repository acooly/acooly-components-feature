package com.acooly.core.test.dubbotest;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.test.dubbo.DemoFacade;
import com.acooly.module.test.meta.DubboTest;
import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qiubo@yiji.com
 */
@RunWith(SpringRunner.class)
@DubboTest
public class DubboConsumerTest {
    protected static final String PROFILE = "sdev";

    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    @Reference(version = "1.0")
    private DemoFacade demoFacade;

    @Test
    public void test() {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto("123");
        demoFacade.echo(request);
    }
}
