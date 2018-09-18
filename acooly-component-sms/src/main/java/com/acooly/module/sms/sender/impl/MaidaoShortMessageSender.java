package com.acooly.module.sms.sender.impl;

import com.acooly.core.utils.Encodes;
import com.acooly.module.sms.sender.ShortMessageSendException;
import org.apache.commons.codec.digest.DigestUtils;
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

/**
 * 北京漫道短发发送实现
 *
 * @author zhangpu
 */
@Service("maidaoShortMessageSender")
public class MaidaoShortMessageSender extends AbstractShortMessageSender {

    public static Map<String, String> messages =
            new LinkedHashMap<String, String>() {
                /** UId */
                private static final long serialVersionUID = -847699194658395108L;

                {
                    put("-2", "帐号/密码不正确");
                    put("-4", "余额不足支持本次发送");
                    put("-5", "数据格式错误");
                    put("-6", "参数有误");
                    put("-7", "权限受限");
                    put("-8", "流量控制错误");
                    put("-9", "扩展码权限错误");
                    put("-10", "内容长度长");
                    put("-11", "内部数据库错误");
                    put("-12", "序列号状态错误");
                    put("-14", "服务器写文件失败");
                    put("-17", "没有权限");
                    put("-19", "禁止同时使用多个接口地址");
                    put("-20", "相同手机号，相同内容重复提交");
                    put("-22", "Ip鉴权失败");
                    put("-23", "缓存无此序列号信息");
                    put("-601", "序列号为空，参数错误");
                    put("-602", "序列号格式错误，参数错误");
                    put("-603", "密码为空，参数错误");
                    put("-604", "手机号码为空，参数错误");
                    put("-605", "内容为空，参数错误");
                    put("-606", "ext长度大于9，参数错误");
                    put("-607", "参数错误 扩展码非数字 ");
                    put("-608", "参数错误 定时时间非日期格式");
                    put("-609", "rrid长度大于18,参数错误 ");
                    put("-610", "参数错误 rrid非数字");
                    put("-611", "参数错误 内容编码不符合规范");
                    put("-623", "手机个数与内容个数不匹配");
                    put("-624", "扩展个数与手机个数数");
                    put("-1000", "网络或未知错误");
                }
            };

    @Override
    public String send(String mobileNo, String content) {
        content = getContent(content);
        String path =
                "/mdsmssend.ashx?sn="
                        + username
                        + "&pwd="
                        + getSign(this.username, this.password)
                        + "&mobile="
                        + mobileNo
                        + "&content="
                        + Encodes.urlEncode(content, "UTF-8");
        String url = service + path;
        logger.debug("短信发送URL：{}", url);
        HttpClient httpclient = new DefaultHttpClient();
        // 请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout / 2);
        // 读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout / 2);
        HttpGet get = new HttpGet(url);
        String result = null;
        String responseBody;
        try {
            HttpResponse response = httpclient.execute(get);
            responseBody = StringUtils.trimToEmpty(EntityUtils.toString(response.getEntity()));
            if (StringUtils.isNotBlank(responseBody)) {
                result = StringUtils.substring(responseBody, 0, 11);
            }
            if (result.contains("-")) {
                throw new ShortMessageSendException(result, messages.get(result));
            }
            logger.info("发送短信完成 {mobile:{},content:{},server:{}}", mobileNo, content, responseBody);
        } catch (ShortMessageSendException smse) {
            logger.warn(
                    "发送短信失败 号码:{}, 内容:{}, result:{}, 原因:{}", mobileNo, content, result, smse.getMessage());
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
        String path = "/mdsmssend.ashx";
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
            data.add(new BasicNameValuePair("sn", this.batchUser));
            data.add(new BasicNameValuePair("pwd", getSign(this.batchUser, this.batchPswd)));
            data.add(new BasicNameValuePair("mobile", StringUtils.join(mobileNos.iterator(), ",")));
            data.add(new BasicNameValuePair("content", getContent(content)));
            post.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
            HttpResponse response = httpclient.execute(post);
            responseBody = StringUtils.trimToEmpty(EntityUtils.toString(response.getEntity()));
            if (StringUtils.isNotBlank(responseBody)) {
                result = StringUtils.substring(responseBody, 0, 11);
            }
            if (result.contains("-")) {
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
        return "北京漫道";
    }

    private String getSign(String user, String pswd) {
        return StringUtils.upperCase(DigestUtils.md5Hex(user + pswd));
    }
}
