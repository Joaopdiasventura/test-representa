package com.test_internship.server.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.test_internship.server.domain.auth.JwtService;
import com.test_internship.server.domain.user.UserService;
import com.test_internship.server.domain.user.entities.User;

import java.io.IOException;

@Component
public class HeaderLoggingFilter implements Filter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    public HeaderLoggingFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            String requestUri = httpRequest.getRequestURI();
            if (requestUri.startsWith("/user")) {
                chain.doFilter(request, response);
                return;
            }

            String authorizationHeader = httpRequest.getHeader("Authorization");

            if (authorizationHeader == null)
                throw new BadRequestException(
                        "Você não tem permissão para realizar essa ação");

            try {
                this.validateAccess(authorizationHeader, requestUri);
            } catch (ResponseStatusException ex) {
                throw new BadRequestException(ex.getMessage());
            }
        }
        chain.doFilter(request, response);
    }

    private void validateAccess(String token, String requestUri) {
        String id = jwtService.decodeToken(token);
        User user = userService.findById(id);
        if (!user.isAdm() && !requestUri.endsWith("/ticket"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Você não tem permissão para realizar essa ação");
    }
}
