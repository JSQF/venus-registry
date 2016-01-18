package com.meidusa.venus.registry.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by huawei on 12/25/15.
 */
@Component
public class UcmAccessDecisionManager implements AccessDecisionManager {



    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        System.out.println(object);
        for(ConfigAttribute attribute : configAttributes) {
            System.out.println(ToStringBuilder.reflectionToString(attribute));
        }

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
