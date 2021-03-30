package com.account.securities;

import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.SecurityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ","").trim();
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = securityService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails) && checkIfUserIsValid(username)) {
                UsernamePasswordAuthenticationToken currentUser =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                currentUser
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(currentUser);
            }
        }
        filterChain.doFilter(request, response);


    }

    private boolean checkIfUserIsValid(String username) throws InvalidTokenException, UserNotFoundInSystem {
        User currentUser = securityService.loadUser(username);
        return currentUser != null && currentUser.getEnabled();
    }
}
