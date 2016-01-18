package com.meidusa.venus.registry.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Created by huawei on 12/26/15.
 */
public class UcmCsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
        if(request.getServletPath() != null && request.getServletPath().endsWith("json")){
            return false;
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }
}
