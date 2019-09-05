package com.acooly.module.security.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.Filter;
import java.util.Map;

/**
 * 扩展 ShiroFilterFactoryBean，支持自定義Filter扩展
 *
 * <p>根据filterChainDefinitions配置里面定义的filter名称,在Spring容器范围内查找相同 名称，并类型为Filter的类，添加/覆盖到shiro-filters中。
 *
 * <p>说明：本来扩展Filter按官方说明和源代码，是通过BeanPostProcessor来进行后置处理，
 * 但是BeanPostProcessor没有成功拦截到自定义的Filter,问题后续在找，先扩展下，使用 init-method方式处理。
 *
 * @author zhangpu
 */
public class ShiroFilterFactoryBeanExtention extends ShiroFilterFactoryBean
        implements ApplicationContextAware {

    private static final Logger logger =
            LoggerFactory.getLogger(ShiroFilterFactoryBeanExtention.class);

    private ApplicationContext applicationContext;

    public void appendCustomFilter() {
        logger.info("Append custome Filter to Shiro-Filters");
        Map<String, String> chains = getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty(chains)) {
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String chainDefinition = entry.getValue();
                Object filter = null;
                try {
                    filter = (Object) applicationContext.getBean(chainDefinition);
                } catch (Exception e) {
                    // 如果找不到自定义的Bean,则忽略
                    continue;
                }
                if (filter != null && filter instanceof Filter) {
                    postProcessBeforeInitialization(filter, chainDefinition);
                    logger.info(
                            "Append/Override filter implements : "
                                    + chainDefinition
                                    + " -- > "
                                    + filter.getClass().getName());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
