package com.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("Root")||roles.contains("Owner")){
            httpServletResponse.sendRedirect("/welcome");
        }
        if(roles.contains("Admin")){
            httpServletResponse.sendRedirect("/manager/user-registration");
        }
        if(roles.contains("Manager")){
            httpServletResponse.sendRedirect("/manager/vendor-registration");
        }
        if(roles.contains("Employee")){
            httpServletResponse.sendRedirect("/invoice/create-invoice");
        }

    }
}
