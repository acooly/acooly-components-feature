/**
 * create by zhangpu date:2015年11月4日
 */
package com.acooly.module.app.notify.jpush;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.app.AppProperties;
import com.acooly.module.app.domain.AppMessage;
import com.acooly.module.app.enums.AppMessageStatus;
import com.acooly.module.app.enums.AppMessageType;
import com.acooly.module.app.notify.AppNotifyService;
import com.acooly.module.app.notify.NotifyMessage;
import com.acooly.module.app.notify.NotifyProperty;
import com.acooly.module.app.notify.jpush.dto.JPushMessage;
import com.acooly.module.app.notify.jpush.dto.JPushNotification;
import com.acooly.module.app.notify.jpush.dto.JPushOptions;
import com.acooly.module.app.service.AppMessageService;
import com.google.common.collect.Lists;

/**
 * 系统通知 JPush实现
 *
 * @author zhangpu
 * @date 2015年11月4日
 */
public class JPushAppNotifyServiceImpl implements AppNotifyService {

	private static final Logger logger = LoggerFactory.getLogger(JPushAppNotifyServiceImpl.class);

	@Resource
	protected JPushSendService jPushSendService;
	@Resource
	protected AppMessageService appMessageService;
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Autowired
	private AppProperties appProperties;

	@Override
	public void group(NotifyMessage notifyMessage, List<String> alias, boolean save) {
		group(notifyMessage, alias, null, null, save);
	}

	@Override
	public void group(NotifyMessage notifyMessage, List<String> alias, String sender, boolean save) {
		group(notifyMessage, alias, null, sender, save);
	}

	@Override
	public void group(NotifyMessage notifyMessage, List<String> alias, List<String> tags, String sender, boolean save) {
		// 极光默认，别名单次-发送 1000
		long defaultSize = 2L;
		long aliasSize = alias.size();
		if (aliasSize > defaultSize) {
			List<String> aliasSub = Lists.newArrayList();
			for (int i = 0; i < aliasSize; i++) {
				aliasSub.add(alias.get(i));
				long aliasSubSize = aliasSub.size();

				// 别名大小 等于 总数；发送当前数据
				if (aliasSize == (i + 1)) {
					pushMsg(notifyMessage, aliasSub, tags, sender, save);
					break;
				}

				// 子对象大小为1000时, 发送当前数据
				if (aliasSubSize % defaultSize == 0) {
					pushMsg(notifyMessage, aliasSub, tags, sender, save);
					aliasSub.clear();
				}

			}
		} else {
			pushMsg(notifyMessage, alias, tags, sender, save);
		}

	}

	private void pushMsg(NotifyMessage notifyMessage, List<String> alias, List<String> tags, String sender,
			boolean save) {
		try {
			JPushOrder order = new JPushOrder();
			// audience<别名>
			if (Collections3.isNotEmpty(alias)) {
				order.getAudience().setAlias(alias);
			}
			// audience<标签>
			if (Collections3.isNotEmpty(tags)) {
				order.getAudience().setTag(tags);
			}

			// message<消息体>
			String title = notifyMessage.getTitle();
			String content = notifyMessage.getContent();
			Map<String, Object> extras = notifyMessage.getContext();

			// message<消息体>
			JPushMessage jpushMessage = new JPushMessage();
			jpushMessage.setTitle(notifyMessage.getTitle());
			jpushMessage.setMsgContent(notifyMessage.getContent());
			Map<String, Object> context = notifyMessage.getContext();
			if (context != null && context.size() > 0) {
				jpushMessage.setExtras(context);
			}
			order.setMessage(jpushMessage);

			// notification && options
			JPushNotification jPushNotification = new JPushNotification();

			// 安卓手机
			JPushNotification.Android android = new JPushNotification.Android();
			android.setAlert(content);
			android.setTitle(title);
			android.setExtras(extras);
			jPushNotification.setAndroid(android);

			// 苹果手机
			JPushNotification.IOS ios = new JPushNotification.IOS();
			ios.setAlert(content);
			ios.setExtras(extras);
			jPushNotification.setIos(ios);
			order.setNotification(jPushNotification);

			boolean online = appProperties.getJpush().isApns() || notifyMessage.isOnline();
			JPushOptions jPushOptions = new JPushOptions(appProperties.getJpush().getTimeToLive(), online);
			order.setOptions(jPushOptions);

			// 扩展参数
			Map<NotifyProperty, Object> properties = notifyMessage.getProperties();
			if (properties != null && properties.size() > 0) {
				JPushNotification jnotification = (JPushNotification) properties.get(NotifyProperty.JPush_Notification);
				if (jnotification != null) {
					order.setNotification(jnotification);
				}
				JPushOptions joptions = (JPushOptions) properties.get(NotifyProperty.JPush_Options);
				if (joptions != null) {
					order.setOptions(joptions);
				}
			}

			JPushResult result = jPushSendService.request(order);
			if (result.getHttpStatus() != 200) {
				throw new RuntimeException(result.getCode() + ":" + result.getMessage());
			}
			logger.info("JPush推送成功: request:{}, result:{}", order, result);
			if (save) {
				// 保存到数据库
				AppMessage appMessage = new AppMessage();
				appMessage.setTitle(notifyMessage.getTitle());
				appMessage.setContent(notifyMessage.getContent());
				if (context != null && context.size() > 0) {
					appMessage.setContext(
							jsonMapper.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(context));
				}

				// 数据库存储(接受群体)
				String receivers = "";
				if (Collections3.isNotEmpty(alias)) {
					order.getAudience().setAlias(alias);
					receivers = receivers + Strings.join(alias, ",") + ",";
				}
				if (Collections3.isNotEmpty(tags)) {
					order.getAudience().setTag(tags);
					receivers = receivers + Strings.join(tags, ",") + ",";
				}
				appMessage.setReceivers(receivers);

				if (Strings.isBlank(sender)) {
					sender = "system";
				}
				appMessage.setSender(sender);
				appMessage.setSendTime(new Date());
				appMessage.setType(Collections3.isNotEmpty(alias) ? AppMessageType.group : AppMessageType.broadcast);
				appMessage.setContentType(notifyMessage.getContentType());
				appMessage.setStatus(AppMessageStatus.success);
				if (properties != null && properties.size() > 0) {
					appMessage.setComments(jsonMapper.toJson(properties));
				}
				appMessageService.save(appMessage);
			}
		} catch (Exception e) {
			logger.error("JPush通知发送失败", e);
			throw new RuntimeException("JPush通知发送失败:" + e.getMessage());
		}
	}

	@Override
	public void broadcast(NotifyMessage notifyMessage, boolean save) {
		group(notifyMessage, null, save);
	}

}
