/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing@yiji.com 2017-03-12 16:10 创建
 */
package com.acooly.core.test.ds;

import com.acooly.core.test.TestNoWebBase;
import org.apache.shiro.util.Assert;
import org.junit.AfterClass;
import org.junit.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author shuijing
 */
public class DatabaseTest extends TestNoWebBase {

    @Resource
    protected DataSource dataSource;

    @AfterClass
    public static void testW() throws Exception {
        System.out.println("a");
    }

    @Test
    public void testDataSource() throws Exception {
        String url = dataSource.getConnection().getMetaData().getURL();
        Assert.notNull(url);
    }
}
