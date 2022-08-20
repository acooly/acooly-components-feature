/**
 * create by zhangpu date:2015年11月4日
 */
package com.acooly.module.app.notify.jpush;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.module.app.AppProperties;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * JPush 发送服务
 *
 * @author zhangpu
 * @date 2015年11月4日
 */
@Service
public class JPushSendService {

    private static final Logger logger = LoggerFactory.getLogger(JPushSendService.class);
    JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
    @Autowired
    private AppProperties appProperties;

    public JPushResult request(JPushOrder order) {
        try {
            if (!appProperties.getJpush().isEnable()) {
                throw new RuntimeException("jpush发送开关已关闭");
            }
            Map<String, String> headMap = Maps.newHashMap();
            headMap.put("Authorization", getAuthorization());
            logger.info("JPush request:{}", order);
            HttpRequest httpRequest = HttpRequest.post(appProperties.getJpush().getGateway()).headers(headMap)
                    .followRedirects(true).contentType(HttpRequest.CONTENT_TYPE_JSON).send(order.toString());
            HttpResult result = new HttpResult();
            result.setBody(httpRequest.body());
            result.setStatus(httpRequest.code());
            logger.info("JPush response:{}", result);
            JPushResult jpushResult = new JPushResult();
            jpushResult.setCode(String.valueOf(result.getStatus()));
            jpushResult.setHttpStatus(result.getStatus());
            if (result.getStatus() == 200) {
                jsonMapper.update(result.getBody(), jpushResult);
            } else {
                JPushReturn jr = jsonMapper.fromJson(result.getBody(), JPushReturn.class);
                jpushResult.setCode(jr.getError().getCode());
                jpushResult.setMessage(jr.getError().getMessage());
                jpushResult.setMsgId(jr.getMsg_id());
                jpushResult.setSendNo(jr.getSendno());
            }
            return jpushResult;
        } catch (Exception e) {
            throw new RuntimeException("JPush 发送失败:" + e.getMessage());
        }
    }

    protected String getAuthorization() {
        String p =
                appProperties.getJpush().getAppKey() + ":" + appProperties.getJpush().getMasterSecret();
        return "Basic " + Base64.encodeBase64String(p.getBytes());
    }

    static class JPushReturn {
        private String msg_id;
        private String sendno;
        private JPushError error;

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getSendno() {
            return sendno;
        }

        public void setSendno(String sendno) {
            this.sendno = sendno;
        }

        public JPushError getError() {
            return error;
        }

        public void setError(JPushError error) {
            this.error = error;
        }
    }

    static class JPushError {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
