package com.meidusa.venus.registry.security;

import com.meidusa.venus.registry.domain.User;
import com.meidusa.venus.registry.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UcmAuthenticationProvider implements AuthenticationProvider {

    private static Logger logger = LoggerFactory.getLogger(UcmAuthenticationProvider.class);

    private AuthenticationProvider delegate;

    @Autowired
    private UserService userService;

    public UcmAuthenticationProvider(AuthenticationProvider delegate) {
        this.delegate= delegate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationDelegate = null;
        try{
            authenticationDelegate = this.delegate.authenticate(authentication);
        }catch(Exception e) {
            e.printStackTrace();
        }

        if (authenticationDelegate == null) {
            return authenticationDelegate;
        }
        User user = null;
        try{
           user  = userService.getUser(authentication.getPrincipal().toString());
        }catch(Exception e) {
            logger.error("", e);
        }

        if (user == null || Boolean.FALSE == user.getActive()) {
            throw new UsernameNotFoundException("");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegate.supports(authentication);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
