package com.project.SmartPick.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DatabaseLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                    HttpServletResponse response,
                                    AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof BadCredentialsException) {
            response.sendRedirect("/login?error");
        } else if (exception instanceof EmailNotVerifiedException) {
            response.sendRedirect("/login?notVerified");
        } else if (exception instanceof InternalAuthenticationServiceException) {
            response.sendRedirect("/login?notVerified");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
