package com.meidusa.venus.registry.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * Created by huawei on 1/7/16.
 */
//@Component
public class FilterInvocationServiceSecurityMetadataSourceBeanPostProcessor implements BeanPostProcessor {

    @Autowired(required = true)
    private UcmFilterInvocationSecurityMetadataSource metadataSource;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof FilterInvocationSecurityMetadataSource) {
            //return metadataSource;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
