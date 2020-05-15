package com.acooly.module.sms.web;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.UserService;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.SmsService;
import com.acooly.module.sms.sender.support.AliyunSmsSendVo;
import com.acooly.module.sms.sender.support.CloopenSmsSendVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acooly.module.security.shiro.filter.CaptchaFormAuthenticationFilter.SMS_VERIFY_CODE_KEY;
import static com.acooly.module.security.shiro.filter.CaptchaFormAuthenticationFilter.SMS_VERIFY_CODE_KEY_ST;

@Slf4j
@Controller
@RequestMapping(value = "/sms/user/login/")
public class UserSmsController {
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    static Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    /**
     * 登录短信验证码发送有效时间，以短信方式发送给用户
     */
    private String smsSendEffectiveTime = "5";

    public static boolean isMobileNO(String mobiles) {
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @RequestMapping(value = "generateSmsCaptcha.json")
    @ResponseBody
    public JsonResult generateSmsCaptcha(
            HttpServletRequest request, HttpServletResponse response, Model model) {
        String username = request.getParameter("username");
        JsonResult result = new JsonResult();
        try {
            if (!securityProperties.isEnableSmsAuth()) {
                throw new RuntimeException("未开启短信验证");
            }
            User user = userService.findUserByUsername(username);
            if (user == null) {
                result.setMessage("用户不存在!");
                result.setSuccess(false);
                return result;
            }
            String mobileNo = user.getMobileNo();
            if (StringUtils.isEmpty(mobileNo)) {
                result.setMessage("手机号码未填，请联系管理员!");
                result.setSuccess(false);
                return result;
            }
            Long lastCurrentTime = (Long) Servlets.getSessionAttribute(SMS_VERIFY_CODE_KEY_ST);
            if (lastCurrentTime != null) {
                long sec = (System.currentTimeMillis() - lastCurrentTime);
                int smsSendInterval = FrameworkPropertiesHolder.get().getSmsSendInterval();
                if (sec < smsSendInterval * 1000) {
                    result.setMessage("发送太频繁，请等" + sec / 1000 + "秒后发送!");
                    result.setSuccess(false);
                    return result;
                }
            }
            String code = RandomStringUtils.randomNumeric(4);

            sendProxy(mobileNo, code, user);

            log.info("短信登录验证 [发送成功] 用户:{}, 手机号码:{}, 验证码:{}", user.getUsername(), mobileNo, code);
            Servlets.setSessionAttribute(SMS_VERIFY_CODE_KEY, code);
            Servlets.setSessionAttribute(SMS_VERIFY_CODE_KEY_ST, System.currentTimeMillis());

            result.setSuccess(true);
            result.setMessage("发送短信验证码成功!");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    void sendProxy(String mobileNo, String code, User user) {
        SmsProperties.Provider provider = smsProperties.getProvider();

        if (provider == SmsProperties.Provider.Aliyun) {
            AliyunSmsSendVo aliyunSmsSendVo = new AliyunSmsSendVo();
            aliyunSmsSendVo.setTemplateCode(smsProperties.getAliyun().getLoginCodeTemplate());
            aliyunSmsSendVo.setFreeSignName(smsProperties.getAliyun().getContentSign());
            Map<String, String> data = Maps.newHashMap();
            data.put("captcha", code);
            aliyunSmsSendVo.setSmsParamsMap(data);
            smsService.send(mobileNo, aliyunSmsSendVo.toJson());
        }

        if (provider == SmsProperties.Provider.Cloopen) {
            //【企账通】尊敬的用户{1}您好，本次登录的验证码为{2}，有效时间{3}分钟，为了您的账户安全，请勿将验证码转告他人。
            CloopenSmsSendVo col = new CloopenSmsSendVo();
            col.setTemplateId(smsProperties.getCloopen().getLoginVerifCodeTemplateId());
            List<String> data = new ArrayList<>();
            data.add(user.getUsername());
            data.add(code);
            data.add(smsSendEffectiveTime);
            col.setDatas(data);
            smsService.send(mobileNo, col.toJson());
        }
        if (provider == SmsProperties.Provider.CL253) {
            smsService.send(mobileNo, buildCotent(code, user));
        }

        if (provider == SmsProperties.Provider.KLUM) {
            smsService.send(mobileNo, buildCotent(code, user));
        }

        if (provider == SmsProperties.Provider.MAIDAO) {
            smsService.send(mobileNo, buildCotent(code, user));
        }
        if (provider == SmsProperties.Provider.EMAY) {
            smsService.send(mobileNo, buildCotent(code, user));
        }
    }

    private String buildCotent(String code, User user) {
        return new StringBuffer()
                .append("尊敬的用户")
                .append(user.getUsername())
                .append("您好")
                .append("本次登录的验证码为")
                .append(code)
                .append(" 有效时间")
                .append(smsSendEffectiveTime)
                .append("分钟，为了您的账户安全，请勿将验证码转告他人。")
                .toString();
    }
}
