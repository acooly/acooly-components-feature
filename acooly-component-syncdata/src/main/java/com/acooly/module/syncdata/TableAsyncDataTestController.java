/*
* acooly.cn Inc.
* Copyright (c) 2023 All Rights Reserved.
* create by acooly
* date:2023-05-06
*/
package com.acooly.module.syncdata;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.syncdata.client.AsyncDataClientService;
import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;
import com.acooly.module.syncdata.manage.service.TableAsyncDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 同步表数据信息 管理控制器
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
@Controller
@RequestMapping(value = "/test/syncdata")
public class TableAsyncDataTestController extends AbstractJsonEntityController<TableAsyncData, TableAsyncDataService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private AsyncDataClientService asyncDataClientService;

	/**
	 * http://127.0.0.1:8090/test/syncdata/index.html
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */

	@RequestMapping({"index"})
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			asyncDataClientService.doAsyncTableData();

			model.addAllAttributes(this.referenceData(request));
		} catch (Exception var5) {
			var5.printStackTrace();
		}

		return this.getListView();
	}
}
