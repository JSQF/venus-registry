package com.meidusa.venus.registry.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huawei on 1/11/16.
 */
public class UcmAccessDeniedHandler implements AccessDeniedHandler {

    public UcmAccessDeniedHandler(){

    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println(accessDeniedException);

        String uri = request.getRequestURI();

        if (StringUtils.hasLength(uri) && uri.endsWith("json")) {
            String accessDeniedJson = "{'code': 401, 'message':'请重新登录'}";
            response.getOutputStream().write(accessDeniedJson.getBytes());
            response.getOutputStream().flush();
        }else {
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
            response.sendRedirect(basePath+"/login/index.htm");
        }

    }
}
