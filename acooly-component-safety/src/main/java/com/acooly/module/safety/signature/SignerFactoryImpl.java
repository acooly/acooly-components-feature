package com.acooly.module.safety.signature;

import com.acooly.module.safety.exception.SafetyException;
import com.acooly.module.safety.exception.SafetyResultCode;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 签名工厂实现
 *
 * @param <T>
 * @author zhangpu
 * @date 2014年6月3日
 */
@Component("safetySignerFactory")
public class SignerFactoryImpl<T, K> implements SignerFactory<T, K>, ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SignerFactoryImpl.class);

    private ApplicationContext applicationContext;

    private Map<String, Signer<T, K>> signerMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        signerMap = Maps.newHashMap();
        Map<String, Signer> signers = applicationContext.getBeansOfType(Signer.class);
        for (Map.Entry<String, Signer> entry : signers.entrySet()) {
            String signName = entry.getValue().getSinType();
            signerMap.put(signName, entry.getValue());
            logger.info("加载{}签名处理器:{}", signName, entry.getValue().getClass().getName());
        }
    }

    @Override
    public Signer<T, K> getSigner(String signType) {
        Signer<T, K> signer = signerMap.get(signType);
        if (signer == null) {
            throw new SafetyException(SafetyResultCode.SIGN_TYPE_UNSUPPORTED, "不支持的signType[" + signType + "]");
        }
        return signer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
