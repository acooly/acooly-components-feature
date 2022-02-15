package com.acooly.module.app.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.app.AppProperties;
import com.acooly.module.app.domain.AppMessage;
import com.acooly.module.app.enums.AppMessageContentType;
import com.acooly.module.app.enums.AppMessageStatus;
import com.acooly.module.app.enums.AppMessageType;
import com.acooly.module.app.notify.AppNotifyService;
import com.acooly.module.app.notify.NotifyMessage;
import com.acooly.module.app.service.AppMessageService;
import com.acooly.module.ofile.OFileProperties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/manage/module/app/appMessage")
public class AppMessageManagerController extends AbstractJQueryEntityController<AppMessage, AppMessageService> {

	private static Map<String, String> allTypes = AppMessageType.mapping();
	private static Map<String, String> allStatuss = AppMessageStatus.mapping();

	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private AppNotifyService appNotifyService;
	@Autowired
	private OFileProperties fileProperties;
	@Autowired
	private AppProperties appProperties;

	/**
	 * 创建页面-定制化
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "customCreate")
	public String customCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
		allow(request, response, MappingMethod.create);
		model.addAllAttributes(referenceData(request));

		// 用户标签
		Map<String, String> tagsMap = appProperties.getJpush().getTags();
		model.addAttribute("tagsMap", tagsMap);

		// 苹果手机跳转（极光扩展参数：iosJump）
		Map<String, String> iosJumpMap = appProperties.getJpush().getIosJump();
		model.addAttribute("iosJumpMap", iosJumpMap);

		// 安卓手机跳转（极光扩展参数：androidJump）
		Map<String, String> androidJumpMap = appProperties.getJpush().getAndroidJump();
		model.addAttribute("androidJumpMap", androidJumpMap);

		return "/manage/module/app/appMessageCustomEdit";
	}

	/**
	 * 定制 群发或广播
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "customPush")
	@ResponseBody
	public JsonEntityResult<AppMessage> customPush(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<AppMessage> result = new JsonEntityResult<AppMessage>();
		try {
			String sessionUser = getSessionUser(request);

			// 扩展参数
			NotifyMessage notifyMessage = getNotifyMessage(request);
			Map<String, Object> contextMaps = notifyMessage.getContext();
			String iosJump = request.getParameter("iosJump");
			if (Strings.isNotBlank(iosJump)) {
				contextMaps.put("iosJump", iosJump);
			}
			String androidJump = request.getParameter("androidJump");
			if (Strings.isNotBlank(androidJump)) {
				contextMaps.put("androidJump", androidJump);
			}

			appNotifyService.group(notifyMessage, getAliass(request), getTagss(request), sessionUser, true);
			result.setMessage("推送成功");
		} catch (Exception e) {
			handleException(result, "推送", e);
		}
		return result;
	}

	/**
	 * 群发或广播
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "push")
	@ResponseBody
	public JsonEntityResult<AppMessage> push(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<AppMessage> result = new JsonEntityResult<AppMessage>();
		try {
			String sessionUser = getSessionUser(request);
			appNotifyService.group(getNotifyMessage(request), getAliass(request), sessionUser, true);
			result.setMessage("推送成功");
		} catch (Exception e) {
			handleException(result, "推送", e);
		}
		return result;
	}

	protected List<String> getTagss(HttpServletRequest request) {
		List<String> tagsList = Lists.newArrayList();
		String[] tagsArray = request.getParameterValues("tags");
		if (tagsArray != null) {
			for (String tags : tagsArray) {
				if (Strings.isNotBlank(tags)) {
					tagsList.add(tags);
				}
			}
		}
		return tagsList;
	}

	protected List<String> getAliass(HttpServletRequest request) {
		String alias = request.getParameter("receivers");
		if (Strings.isNotBlank(alias)) {
			return Lists.newArrayList(Strings.split(alias, ","));
		} else {
			return null;
		}
	}

	protected NotifyMessage getNotifyMessage(HttpServletRequest request) {
		NotifyMessage nm = new NotifyMessage();
		nm.setTitle(request.getParameter("title"));
		nm.setContent(request.getParameter("content"));
		String online = request.getParameter("online");
		nm.setOnline(Strings.equalsIgnoreCase("true", online));
		String context = request.getParameter("context");
		String contentType = request.getParameter("contentType");
		if (Strings.isBlank(contentType)) {
			nm.setContentType(AppMessageContentType.normal);
		} else {
			nm.setContentType(AppMessageContentType.findStatus(contentType));
		}
		if (Strings.isNotBlank(context)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> contextMap = JsonMapper.nonDefaultMapper().fromJson(context, Map.class);
			nm.setContext(contextMap);
		} else {
			nm.setContext(Maps.newHashMap());
		}
		Validators.assertJSR303(nm);
		return nm;
	}

	protected String getSessionUser(HttpServletRequest request) {
		String[] keys = Strings.split(fileProperties.getCheckSessionKey(), ",");
		Object user = null;
		for (String key : keys) {
			user = request.getSession().getAttribute(key);
			if (user != null) {
				break;
			}
		}
		if (user != null) {
			return user.toString();
		}
		return null;
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", allTypes);
		model.put("allStatuss", allStatuss);
		model.put("allContentTypes", AppMessageContentType.mapping());
	}
}
