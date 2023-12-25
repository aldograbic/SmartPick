package com.project.SmartPick.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.project.SmartPick.classes.user.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    CustomUserDetailsService userService;

    @Autowired
    UserRepository userRepository;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        handleRoleBasedRedirect(authentication, response);
    }

    private void handleRoleBasedRedirect(Authentication authentication, HttpServletResponse response) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/admin-dashboard");
        } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {
            response.sendRedirect("/user-dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}
