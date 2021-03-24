/**
 * create by zhangpu date:2015年7月14日
 */
package com.acooly.module.ofile.auth;

import com.acooly.core.common.exception.CommonErrorCodes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * OFile上传认证代理实现
 *
 * <p>如果使用端需要对上传文件进行身份验证，可以自行实现该接口并放置于spring容器，组件会自动加载并使用客户化实现。 多个认证实现，只要一个通过则表示认证通过
 *
 * @author zhangpu
 */
@Primary
@Service("ofileUploadAuthenticateSpringProxy")
public class OFileUploadAuthenticateSpringProxy
        implements OFileUploadAuthenticate, ApplicationContextAware, InitializingBean {

    private static final Logger logger =
            LoggerFactory.getLogger(OFileUploadAuthenticateSpringProxy.class);
    private ApplicationContext applicationContext;
    private Map<String, OFileUploadAuthenticate> servicesMap = Maps.newHashMap();

    @Override
    public void authenticate(HttpServletRequest request) {

        if (servicesMap.isEmpty()) {
            logger.info("没有一个认证实现通过，不进行上传认证");
            return;
        }

        List<String> messages = Lists.newArrayList();
        for (Map.Entry<String, OFileUploadAuthenticate> entry : servicesMap.entrySet()) {
            try {
                entry.getValue().authenticate(request);
                return;
            } catch (Exception e) {
                messages.add(e.getMessage());
                logger.debug("认证器（" + entry.getValue().getClass() + "）认证失败.");
            }
        }
        logger.info("没有一个认证实现通过，判断本次上传认证失败:{}", messages);
        throw new OFileUploadException(CommonErrorCodes.UNAUTHENTICATED_ERROR, "文件上传认证未通过：" + messages);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        servicesMap = Maps.newHashMap();
        Map<String, OFileUploadAuthenticate> services =
                applicationContext.getBeansOfType(OFileUploadAuthenticate.class);
        for (Map.Entry<String, OFileUploadAuthenticate> entry : services.entrySet()) {
            if (entry.getValue() == this || !entry.getValue().isEnable()) {
                continue;
            }
            servicesMap.put(entry.getKey(), entry.getValue());
            logger.info("加载OFile认证实现:{}", entry.getKey());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
