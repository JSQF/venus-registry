package com.meidusa.venus.registry.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public void logoutAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getRequestDispatcher("/login/index.htm").forward(request, response);
    }
}
