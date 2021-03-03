/**
 * create by zhangpu date:2015年7月14日
 */
package com.acooly.module.ofile.auth;

import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Signature 检查认证
 *
 * @author zhangpu
 */
@Slf4j
public abstract class AbstractSignatureOFileUploadAuthenticate implements OFileUploadAuthenticate {

    private static final int EXPIRE_SECONDS = 20;

    @Override
    public void authenticate(HttpServletRequest request) {


        try {
            String accessKey = request.getParameter("accessKey");
            String fileName = request.getParameter("fileName");
            String sign = request.getParameter("sign");
            String timestamp = Servlets.getParameter(request, "timestamp");
            Assert.hasLength(accessKey, "访问码不能为空");
            Assert.hasLength(fileName, "文件名不能为空");
            Assert.hasLength(sign, "签名不能为空");
            Assert.notNull(timestamp, "时间搓不能为空");

            Date timestampData = null;
            try {
                timestampData = Dates.parse(timestamp);
            } catch (Exception e) {
                throw new IllegalArgumentException("timestamp格式错误(yyyy-MM-dd HH:mm:ss)。");
            }

            if (Math.abs((new Date()).getTime() - timestampData.getTime()) > getExpireSeconds() * 60 * 1000) {
                throw new IllegalArgumentException("认证已经过期");
            }

            String signWaiting = "accessKey=" + accessKey + "&fileName=" + fileName + "&timestamp=" + timestamp;
            // 子类可扩展点
            signWaiting = doSignWaiting(request, signWaiting);

            String serverSign = DigestUtils.md5Hex(signWaiting + getSecretKey(accessKey));
            if (!Strings.equals(serverSign, sign)) {
                log.warn("OFile签名认证 [失败] 签名认证未通过。clientSign:{}, serverSign:{}, serverSignWaiting：{}", sign, serverSign, signWaiting);
                throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "签名认证未通过");
            }

            log.info("OFile签名认证 [成功] fileName:{},accessKey:{} ", fileName, accessKey);
        } catch (IllegalArgumentException ie) {
            log.warn("OFile签名认证 [失败] 参数格式错误。 detail：{}", ie.getMessage());
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, ie.getMessage());
        } catch (HttpServerErrorException be) {
            throw be;
        } catch (Exception e) {
            log.warn("OFile签名认证 [失败] 内部处理异常。原因：{}", e.getMessage());
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "认证处理失败");
        }
    }


    /**
     * 获取待签名字符串
     *
     * @param request
     * @return
     */
    protected String doSignWaiting(HttpServletRequest request, String signWaiting) {
        return signWaiting;
    }

    /**
     * 获取安全秘钥
     *
     * @param accessKey
     * @return
     */
    protected abstract String getSecretKey(String accessKey);


    protected int getExpireSeconds() {
        return EXPIRE_SECONDS;
    }

}
