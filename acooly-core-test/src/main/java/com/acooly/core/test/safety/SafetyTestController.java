package com.acooly.core.test.safety;

import com.acooly.core.utils.Strings;
import com.acooly.module.safety.Safes;
import com.acooly.module.safety.signature.SignTypeEnum;
import com.acooly.module.safety.signature.Signer;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangpu
 */
@Slf4j
@Controller
@RequestMapping("safety")
public class SafetyTestController {

    @RequestMapping("signDigest")
    @ResponseBody
    public Object sign(HttpServletRequest request) {
        String key = Strings.isBlankDefault(request.getParameter("key"), "123123123");
        String signType = Strings.isBlankDefault(request.getParameter("signType"), SignTypeEnum.MD5Hex.name());
        return doDigestSignTest(signType, key);
    }


    protected Map<String, Object> doDigestSignTest(String signType, String key) {
        Signer signer = Safes.getSigner(signType);
        Map<String, String> plain = getPlainMap();
        log.info("signerType:{}, key: {}", signer.getSinType(), key);
        log.info("signerType:{}, plain: {}", signer.getSinType(), plain);
        String signature = signer.sign(plain, key);
        log.info("signerType:{}, signature: {}", signer.getSinType(), signature);

        boolean verifyResult = false;
        try {
            signer.verify(plain, key, signature);
            verifyResult = true;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("signerType:{}, verify: {} ", signer.getSinType(), verifyResult);
        Map<String, Object> result = Maps.newHashMap();
        result.put("signType", signer.getSinType());
        result.put("key", key);
        result.put("plain", plain);
        result.put("signature", signature);
        result.put("verify", verifyResult);
        return result;
    }

    private Map<String, String> getPlainMap() {
        Map<String, String> plain = Maps.newHashMap();
        plain.put("1", "12312312");
        plain.put("a", "12asdf");
        plain.put("asdf", "2323");
        plain.put("ccc", "中国人");
        return plain;
    }

}
