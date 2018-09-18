/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-21 22:39 创建
 */
package com.acooly.module.appservice;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.module.appservice.ex.ExceptionHandler;
import com.acooly.module.appservice.ex.ExceptionHandlers;
import com.acooly.module.appservice.filter.AppServiceFilterChain;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.Ordered;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author qiubo@yiji.com
 */
public class AppServiceBeanPostProcessor
        implements BeanPostProcessor,
        Ordered,
        ApplicationContextAware,
        BeanDefinitionRegistryPostProcessor,
        InitializingBean,
        BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(AppServiceBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    private AppServiceFilterChain appServiceFilterChain;

    private AppServiceProperties appServiceProperties;

    private BeanFactory beanFactory;

    private boolean checkBPP = false;

    private List<String> scanPackageList;

    public AppServiceBeanPostProcessor(AppServiceProperties appServiceProperties) {
        this.appServiceProperties = appServiceProperties;
        scanPackageList =
                Splitter.on(",")
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(appServiceProperties.getAppServiceScanPackage());
    }

    public static boolean isCglibProxyClass(Class clazz) {
        return clazz != null && clazz.getName().contains("$$");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        return bean;
    }

    private Class<?> getTargetClass(Object bean) {
        Class targetClass;
        for (targetClass = AopUtils.getTargetClass(bean);
             isCglibProxyClass(targetClass);
             targetClass = targetClass.getSuperclass()) {
        }
        return targetClass;
    }

    private int getDubboBPPIndex(List<BeanPostProcessor> beanPostProcessors) {
        for (int i = 0; i < beanPostProcessors.size(); i++) {
            BeanPostProcessor beanPostProcessor = beanPostProcessors.get(i);
            if (beanPostProcessor
                    .getClass()
                    .getName()
                    .equals("com.alibaba.dubbo.config.spring.AnnotationBean")) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    private int getFacadeBPPIndex(List<BeanPostProcessor> beanPostProcessors) {
        for (int i = 0; i < beanPostProcessors.size(); i++) {
            BeanPostProcessor beanPostProcessor = beanPostProcessors.get(i);
            if (beanPostProcessor.getClass().equals(AppServiceBeanPostProcessor.class)) {
                return i;
            }
        }
        return 0;
    }

    private int getAOPBPPIndex(List<BeanPostProcessor> beanPostProcessors) {
        for (int i = 0; i < beanPostProcessors.size(); i++) {
            BeanPostProcessor beanPostProcessor = beanPostProcessors.get(i);
            if (AbstractAdvisorAutoProxyCreator.class.isAssignableFrom(beanPostProcessor.getClass())) {
                return i;
            }
        }
        return -1;
    }

    private void checkBPPOrder() {
        if (beanFactory instanceof AbstractBeanFactory) {
            List<BeanPostProcessor> beanPostProcessors =
                    ((AbstractBeanFactory) beanFactory).getBeanPostProcessors();
            int dubboIndex = getDubboBPPIndex(beanPostProcessors);
            int facadeIndex = getFacadeBPPIndex(beanPostProcessors);
            int aopIndex = getAOPBPPIndex(beanPostProcessors);
            if (dubboIndex < facadeIndex) {
                throw new AppConfigException(
                        "为了保证@FacadeService正确提供能力，需要保证dubbo BPP(AnnotationBean)在facade BPP后面");
            }
            if (aopIndex > facadeIndex) {
                throw new AppConfigException(
                        "为了保证@FacadeService正确提供能力，需要保证aop BPP(AbstractAdvisorAutoProxyCreator及其子类，目前可能是InfrastructureAdvisorAutoProxyCreator)在facade BPP前面");
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!checkBPP) {
            checkBPPOrder();
            checkBPP = true;
        }
        Class targetClass = this.getTargetClass(bean);
        Class oriTargetClass = targetClass;
        if (isInAppServiceScanPackage(targetClass)) {
            if (appServiceFilterChain == null) {
                appServiceFilterChain = applicationContext.getBean(AppServiceFilterChain.class);
            }
            AppServiceMethodInterceptor interceptor =
                    new AppServiceMethodInterceptor(bean, appServiceFilterChain);
            do {
                Method[] declaredMethods = targetClass.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (Modifier.isPublic(declaredMethod.getModifiers())
                            && !Modifier.isStatic(declaredMethod.getModifiers())
                            && !Modifier.isAbstract(declaredMethod.getModifiers())) {
                        interceptor.register(declaredMethod, oriTargetClass);
                    }
                }
                targetClass = targetClass.getSuperclass();
            } while (!targetClass.equals(Object.class));

            if (interceptor.canBeProxy()) {
                ProxyFactory factory = new ProxyFactory();
                factory.setTarget(bean);
                factory.addAdvice(interceptor);
                //for performance tuning
                factory.setOptimize(true);
                factory.setFrozen(true);
                factory.setProxyTargetClass(true);
                return factory.getProxy();
            }
        }
        return bean;
    }

    /**
     * 判断类路径是否在appServiceScanPackage配置中
     *
     * @param targetClass
     * @return
     */
    private boolean isInAppServiceScanPackage(Class targetClass) {
        return scanPackageList
                .stream()
                .anyMatch(scanPackage -> targetClass.getName().startsWith(scanPackage));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        scanner.addIncludeFilter(new AssignableTypeFilter(ExceptionHandler.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(ExceptionHandlers.class));
        scanner.scan(
                Splitter.on(",")
                        .omitEmptyStrings()
                        .trimResults()
                        .splitToList(appServiceProperties.getExceptionHanderScanPackage())
                        .toArray(new String[0]));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
