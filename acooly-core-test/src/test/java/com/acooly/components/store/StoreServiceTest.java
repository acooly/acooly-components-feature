/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-09-01 10:20 创建
 */
package com.acooly.components.store;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.test.AppTestBase;

/**
 * @author zhangpu 2017-09-01 10:20
 */
public class StoreServiceTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

//
//    @Autowired
//    private StoreService storeService;
//
//
//    @Test
//    public void testLoadStore() {
//        String storeCode = "00000001";
//        List<StoreNode> storeNodeList = storeService.loadStore(storeCode);
//        System.out.println(storeNodeList);
//    }
//

}
