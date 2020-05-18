/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 23:20
 */
package com.acooly.module.smsend.service.impl;

import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.filterchain.FilterChain;
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.dto.SenderInfo;
import com.acooly.module.smsend.entity.SmsSendLog;
import com.acooly.module.smsend.common.enums.SmsSendStatus;
import com.acooly.module.smsend.common.enums.SmsSendType;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.filter.SmsSendContext;
import com.acooly.module.smsend.filter.SmsSendFilterChain;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.acooly.module.smsend.manage.SmsSendLogService;
import com.acooly.module.smsend.service.SmsSendService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信发送服务
 *
 * @author zhangpu
 * @date 2020-04-12 23:20
 */
@Slf4j
@Component
public class SmsSendServiceImpl implements SmsSendService {
    @Autowired
    protected SmsSendProperties smsSendProperties;
    @Resource
    private ShortMessageSender shortMessageSender;
    @Resource
    private SmsSendLogService smsSendLogService;

    @Resource
    private SmsSendFilterChain smsSendFilterChain;

    @Resource(name = "smsSendFilterChain")
    private FilterChain<SmsSendContext> filter;

    @Resource(name = "commonTaskExecutor")
    private TaskExecutor taskExecutor;


    @Override
    public void send(SenderInfo senderInfo) {
        if (senderInfo.isAsync()) {
            taskExecutor.execute(() -> doSend(senderInfo));
        } else {
            doSend(senderInfo);
        }
    }

    protected void doSend(SenderInfo senderInfo) {
        SmsResult smsResult = null;
        try {
            doCheck(senderInfo);
            if (senderInfo.getMobileNos().size() == 1) {
                if (senderInfo.getSmsSendType() == SmsSendType.template) {
                    smsResult = shortMessageSender.sendTemplate(Collections3.getFirst(senderInfo.getMobileNos()),
                            senderInfo.getTemplateCode(), senderInfo.getTemplateParams(),
                            senderInfo.getContentSign());
                } else {
                    smsResult = shortMessageSender.send(Collections3.getFirst(senderInfo.getMobileNos()),
                            senderInfo.getContent(), senderInfo.getContentSign());
                }
            } else {
                if (senderInfo.getSmsSendType() == SmsSendType.template) {
                    smsResult = shortMessageSender.sendTemplate(senderInfo.getMobileNos(),
                            senderInfo.getTemplateCode(), senderInfo.getTemplateParams(),
                            senderInfo.getContentSign());
                } else {
                    smsResult = shortMessageSender.send(senderInfo.getMobileNos(),
                            senderInfo.getContent(), senderInfo.getContentSign());
                }

            }
            if (!smsResult.isSuccess()) {
                throw new ShortMessageSendException(smsResult);
            }
        } catch (ShortMessageSendException se) {
            throw se;
        } catch (Exception e) {
            log.error("短信发送服务 [错误] 发送未知异常", e);
            throw new ShortMessageSendException(CommonErrorCodes.INTERNAL_ERROR);
        } finally {
            saveLog(senderInfo, smsResult);
        }
    }


    /**
     * 保存发送日志
     *
     * @param senderInfo
     * @param smsResult
     */
    protected void saveLog(SenderInfo senderInfo, SmsResult smsResult) {
        SmsSendLog sendLog = new SmsSendLog();
        BeanCopier.copy(senderInfo, sendLog);
        sendLog.setSendType(senderInfo.getSmsSendType());
        // 如果是模板模式，保存模板数据为JSON格式
        if (senderInfo.getSmsSendType() == SmsSendType.template) {
            sendLog.setTemplateJsonParams(JSON.toJSONString(senderInfo.getTemplateParams()));
        }
        // 已发送
        if (smsResult != null) {
            BeanCopier.copy(smsResult, sendLog);
            sendLog.setStatus(smsResult.isSuccess() ? SmsSendStatus.SUCCESS : SmsSendStatus.FAIL);
        }

        if (senderInfo.getMobileNos().size() == 1) {
            sendLog.setMobileNo(Collections3.getFirst(senderInfo.getMobileNos()));
            smsSendLogService.saveInNewTrans(sendLog);
        } else {
            List<SmsSendLog> sendlogs = Lists.newArrayList();
            try {
                String batchNo = Ids.getDid();
                for (String mobileNo : senderInfo.getMobileNos()) {
                    SmsSendLog clone = sendLog.clone();
                    clone.setMobileNo(mobileNo);
                    clone.setBatchNo(batchNo);
                    sendlogs.add(clone);
                }
            } catch (Exception e) {
                // ig
            }
            smsSendLogService.savesInNewTrans(sendlogs);
        }
    }


    /**
     * 参数合法性检查
     *
     * @param senderInfo
     */
    protected void doCheck(SenderInfo senderInfo) {
        Validators.assertJSR303(senderInfo);
        senderInfo.check();
        SmsSendContext content = new SmsSendContext();
        BeanCopier.copy(senderInfo, content);
        filter.doFilter(content);
    }

}
