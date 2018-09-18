package com.acooly.module.sms;

import com.acooly.module.sms.domain.SmsLog;
import com.acooly.module.sms.enums.SmsStatus;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.acooly.module.sms.sender.ShortMessageSender;
import com.acooly.module.sms.service.SmsBlacklistService;
import com.acooly.module.sms.service.SmsLogService;
import com.acooly.module.sms.template.SmsTemplate;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信发送服务
 *
 * <p>发送，模板和日志整合
 *
 * @author zhangpu
 */
@Service("SmsService")
public class SmsServiceImpl implements SmsService, InitializingBean {
    public static final int ONE_SECOND = 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Resource
    private ShortMessageSender shortMessageSender;
    @Resource
    private SmsTemplate smsTemplate;
    @Resource
    private SmsLogService smsLogService;
    @Resource
    private SmsBlacklistService smsBlacklistService;

    @Resource(name = "commonTaskExecutor")
    private TaskExecutor taskExecutor;

    @Autowired
    private SmsProperties smsProperties;
    private int ipFreq = 2000;
    private int sendInterval = 1;

    public void afterPropertiesSet() throws Exception {
        ipFreq = smsProperties.getIpFreq();
        sendInterval = smsProperties.getSendInterval();
    }

    @Override
    public void send(String mobileNo, String content, SmsContext context) {
        String result = null;
        SmsStatus smsStatus = SmsStatus.SEND_FAIL;
        String resultMessage = null;
        try {
            if (isInBlacklist(mobileNo)) {
                return;
            }
            sendCheck(mobileNo, context);
            result = shortMessageSender.send(mobileNo, content);
            smsStatus = SmsStatus.SEND_SUCCESS;
        } catch (ShortMessageSendException e) {
            result = e.getResultCode();
            resultMessage = e.getMessage();
            throw e;
        } finally {
            saveLog(mobileNo, content, smsStatus, result, resultMessage, context);
        }
    }

    @Override
    public void send(String mobileNo, String content) {
        send(mobileNo, content, null);
    }

    @Override
    public void sendAsync(final String mobileNo, final String content, final SmsContext smsContext) {
        if (isInBlacklist(mobileNo)) {
            return;
        }
        sendCheck(mobileNo, smsContext);
        if (smsContext != null) {
            smsContext.setNeedCheck(false);
        }
        taskExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        send(mobileNo, content, smsContext);
                    }
                });
    }

    @Override
    public void sendAsync(final String mobileNo, final String content) {
        sendAsync(mobileNo, content, null);
    }

    @Override
    public void sendByTemplate(
            String mobileNo, String template, Map<String, Object> data, SmsContext smsContext) {
        String content = null;
        try {
            content = smsTemplate.getMessage(template, data);
        } catch (Exception e) {
            logger.error("根据模板生成短信失败:{}", e.getMessage());
            return;
        }
        send(mobileNo, content, smsContext);
    }

    @Override
    public void sendByTemplate(String mobileNo, String template, Map<String, Object> data) {
        sendByTemplate(mobileNo, template, data, null);
    }

    @Override
    public void sendByTemplateAsync(
            final String mobileNo, final String template, final Map<String, Object> data) {
        sendByTemplateAsync(mobileNo, template, data, null);
    }

    @Override
    public void sendByTemplateAsync(
            final String mobileNo,
            final String template,
            final Map<String, Object> data,
            final SmsContext smsContext) {
        if (isInBlacklist(mobileNo)) {
            return;
        }
        sendCheck(mobileNo, smsContext);
        if (smsContext != null) {
            smsContext.setNeedCheck(false);
        }
        taskExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        sendByTemplate(mobileNo, template, data, smsContext);
                    }
                });
    }

    @Override
    public void batchSend(List<String> mobileNos, String content) {
        String result = null;
        SmsStatus smsStatus = SmsStatus.SEND_FAIL;
        String resultMessage = null;
        try {
            result = shortMessageSender.send(mobileNos, content);
            smsStatus = SmsStatus.SEND_SUCCESS;
        } catch (ShortMessageSendException e) {
            smsStatus = SmsStatus.SEND_FAIL;
            result = e.getResultCode();
            resultMessage = e.getMessage();
            throw e;
        } finally {
            saveLogs(mobileNos, content, smsStatus, result, resultMessage);
        }
    }

    @Override
    public void batchSendByTemplate(
            List<String> mobileNos, String template, Map<String, Object> data) {
        String content = smsTemplate.getMessage(template, data);
        batchSend(mobileNos, content);
    }

    protected void saveLog(
            String mobileNo,
            String content,
            SmsStatus smsStatus,
            String result,
            String resultMessage,
            SmsContext context) {
        SmsLog log = new SmsLog(mobileNo, content);
        log.setProvider(shortMessageSender.getProvider());
        log.setProviderStatus(result);
        log.setProviderMemo(resultMessage);
        log.setStatus(smsStatus.getCode());
        if (context != null) {
            log.setClientIp(context.getClientIp());
            log.setComments(context.getComments());
        }
        smsLogService.save(log);
    }

    protected void saveLogs(
            List<String> mobileNos,
            String content,
            SmsStatus smsStatus,
            String result,
            String resultMessage) {
        SmsLog log = null;
        List<SmsLog> logs = Lists.newArrayList();
        String batchNo = String.valueOf(new Date().getTime());
        for (String mobileNo : mobileNos) {
            log = new SmsLog(mobileNo, content);
            log.setBatchNo(batchNo);
            log.setProvider(shortMessageSender.getProvider());
            log.setProviderStatus(result);
            log.setProviderMemo(resultMessage);
            log.setStatus(smsStatus.getCode());
            logs.add(log);
        }
        smsLogService.saves(logs);
    }

    protected boolean isBlacklist(String mobileNo) {
        return smsBlacklistService.inBlacklist(mobileNo);
    }

    private boolean isInBlacklist(String mobileNo) {
        if (isBlacklist(mobileNo)) {
            logger.info("{}:黑名单用户，不予发送", mobileNo);
            return true;
        }
        return false;
    }

    protected void sendCheck(String mobileNo, SmsContext context) {

        if (context == null || !context.isNeedCheck()) {
            return;
        }
        if (StringUtils.isNotBlank(context.getClientIp())) {
            int count = smsLogService.countByIp(context.getClientIp(), ONE_SECOND);
            if (count > ipFreq) {
                throw new ShortMessageSendException("-1001", "单IP每分钟发送量过大(最大:" + ipFreq + ")");
            }
        }
        int count = smsLogService.countByMobileNo(mobileNo, sendInterval * 1000);
        if (count > 0) {
            throw new ShortMessageSendException(
                    "-1002", "手机号码(" + mobileNo + ")发送频率(间隔" + sendInterval + "秒)过大");
        }
    }
}
