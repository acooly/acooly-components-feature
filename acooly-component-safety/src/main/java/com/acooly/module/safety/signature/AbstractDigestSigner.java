package com.acooly.module.safety.signature;

import com.acooly.core.utils.Strings;
import com.acooly.module.safety.exception.SignatureVerifyException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhangpu on 2015/1/23.
 */
public abstract class AbstractDigestSigner extends AbstractSigner<Map<String, String>, String> {


    @Override
    protected String getWaitToSigin(Map<String, String> plain) {
        String waitToSignStr = null;
        Map<String, String> sortedMap = new TreeMap<>(plain);
        if (sortedMap.containsKey("sign")) {
            sortedMap.remove("sign");
        }
        StringBuilder stringToSign = new StringBuilder();
        if (sortedMap.size() > 0) {
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                if (entry.getValue() != null) {
                    stringToSign.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
            stringToSign.deleteCharAt(stringToSign.length() - 1);
            waitToSignStr = stringToSign.toString();
        }
        return waitToSignStr;
    }

    @Override
    protected void doVerify(String waitForSign, String key, String sign) {
        String calcSign = doSign(waitForSign, key);
        if (!Strings.equals(sign, calcSign)) {
            throw new SignatureVerifyException();
        }
    }


}
