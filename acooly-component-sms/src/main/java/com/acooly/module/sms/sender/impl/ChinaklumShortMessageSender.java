package com.acooly.module.sms.sender.impl;

import com.acooly.core.utils.Encodes;
import com.acooly.module.sms.sender.ShortMessageSendException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("chinaklumShortMessageSender")
public class ChinaklumShortMessageSender extends AbstractShortMessageSender {

    public static Map<String, String> messages =
            new LinkedHashMap<String, String>() {
                /** UId */
                private static final long serialVersionUID = -847699194658395108L;

                {
                    put("-1000", "网络或未知错误");
                    put("-1", "账号未注册");
                    put("-2", "其他错误");
                    put("-3", "密码错误");
                    put("-4", "手机号格式不对");
                    put("-5", "余额不足");
                    put("-6", "时间格式错误");
                    put("-7", "10小时内重复");
                }
            };

    /**
     * 发送短信
     *
     * @param mobileNo
     * @param content
     * @return 大于0表示状态报告的ID，小于0表示错误
     */
    public String send(String mobileNo, String content) {
        String path =
                "/Send2.aspx?CorpID="
                        + username
                        + "&Pwd="
                        + password
                        + "&Mobile="
                        + mobileNo
                        + "&Content="
                        + Encodes.urlEncode(getContent(content), "GB2312")
                        + "&Cell=&SendTime=";
        String url = service + path;
        return doGetSend(url, mobileNo, content);
    }

    protected String doGetSend(String url, String mobileNo, String content) {
        logger.debug("短信发送URL：{}", url);
        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout / 2);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout / 2);
        HttpGet get = new HttpGet(url);
        String result = null;
        String responseBody = null;
        try {
            HttpResponse response = httpclient.execute(get);
            responseBody = StringUtils.trimToEmpty(EntityUtils.toString(response.getEntity()));
            if (StringUtils.isNotBlank(responseBody)) {
                result = StringUtils.substring(responseBody, 0, 11);
            }
            if (Integer.parseInt(result) < 0) {
                throw new ShortMessageSendException(result, messages.get(result));
            }
            logger.info("发送短信完成 {mobile:{},content:{},server:{}}", mobileNo, content, responseBody);
        } catch (ShortMessageSendException smse) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + content + "}, 原因:" + smse.getMessage());
            throw smse;
        } catch (Exception e) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + content + "}, 原因:" + e.getMessage());
            throw new ShortMessageSendException("-1000", messages.get("-1000"), e.getMessage());
        } finally {
            get.releaseConnection();
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        String path = "/BatchSend.aspx";
        String url = service + path;
        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout / 2);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout / 2);
        HttpPost post = new HttpPost(url);
        String result = null;
        String responseBody = null;
        try {
            List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
            data.add(new BasicNameValuePair("CorpID", this.batchUser));
            data.add(new BasicNameValuePair("Pwd", this.batchPswd));
            data.add(new BasicNameValuePair("Mobile", StringUtils.join(mobileNos.iterator(), ",")));
            data.add(new BasicNameValuePair("Content", getContent(content)));
            post.setEntity(new UrlEncodedFormEntity(data, "GB2312"));
            HttpResponse response = httpclient.execute(post);
            responseBody = StringUtils.trimToEmpty(EntityUtils.toString(response.getEntity()));
            if (StringUtils.isNotBlank(responseBody)) {
                result = StringUtils.substring(responseBody, 0, 11);
            }
            if (Integer.parseInt(result) < 0) {
                throw new ShortMessageSendException(result, messages.get(result));
            }
            logger.info("批量发送短信完成 {mobile:{},content:{},server:{}}", mobileNos, content, responseBody);
        } catch (ShortMessageSendException smse) {
            logger.warn("批量发送短信失败 {号码:" + mobileNos + ",内容:" + content + "}, 原因:" + smse.getMessage());
            throw smse;
        } catch (Exception e) {
            logger.warn("批量发送短信失败 {号码:" + mobileNos + ",内容:" + content + "}, 原因:" + e.getMessage());
            throw new ShortMessageSendException("-1000", messages.get("-1000"), e.getMessage());
        } finally {
            post.releaseConnection();
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }

    @Override
    public String getProvider() {
        return "重庆客亲通";
    }
}
