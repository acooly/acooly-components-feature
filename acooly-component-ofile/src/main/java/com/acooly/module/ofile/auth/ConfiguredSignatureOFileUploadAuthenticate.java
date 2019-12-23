package com.acooly.module.ofile.auth;

import com.acooly.core.utils.Strings;
import com.acooly.module.ofile.OFileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 可配置的上传签名认证器
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-29 01:54
 */
@Component
public class ConfiguredSignatureOFileUploadAuthenticate extends AbstractSignatureOFileUploadAuthenticate {

    @Autowired
    private OFileProperties oFileProperties;


    @Override
    public void authenticate(HttpServletRequest request) {
        if (!oFileProperties.isConfiguredSignAuthEnable()) {
            return;
        }
        super.authenticate(request);
    }

    @Override
    protected String getSecretKey(String accessKey) {
        if (Strings.equals(oFileProperties.getConfiguredSignAuthAccessKey(), accessKey)) {
            return oFileProperties.getConfiguredSignAuthSecretKey();
        }
        throw new IllegalArgumentException("accessKey非法");
    }

    @Override
    public boolean isEnable() {
        return oFileProperties.isConfiguredSignAuthEnable();
    }
}
