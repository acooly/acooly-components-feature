/**
 * create by zhangpu date:2015年7月14日
 */
package com.acooly.module.ofile.auth;

import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Strings;
import com.acooly.module.ofile.OFileProperties;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * session 检查认证
 *
 * @author zhangpu
 */
@Service
public class OFileUploadSessionAuthenticate implements OFileUploadAuthenticate {
    @Autowired
    private OFileProperties oFileProperties;

    @Override
    public void authenticate(HttpServletRequest request) {
        if (!oFileProperties.isCheckSession() || Strings.isBlank(oFileProperties.getCheckSessionKey())) {
            return;
        }
        List<String> sessionKeys =
                Lists.newArrayList(Strings.split(oFileProperties.getCheckSessionKey(), ","));
        for (String sessionKey : sessionKeys) {
            if (request.getSession().getAttribute(sessionKey) != null) {
                return;
            }
        }
        throw new OFileUploadException(CommonErrorCodes.UNAUTHENTICATED_ERROR, "OFile文件上传Session认证未通过");
    }


    @Override
    public boolean isEnable() {
        return oFileProperties.isCheckSession() && Strings.isNotBlank(oFileProperties.getCheckSessionKey());
    }

}
