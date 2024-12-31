package com.test_internship.server.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test_internship.server.domain.auth.JwtService;
import com.test_internship.server.domain.user.UserService;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HeaderLoggingFilter> loggingFilter(JwtService jwtService, UserService userService) {
        FilterRegistrationBean<HeaderLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        HeaderLoggingFilter filter = new HeaderLoggingFilter(jwtService, userService);

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("HeaderLoggingFilter");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
