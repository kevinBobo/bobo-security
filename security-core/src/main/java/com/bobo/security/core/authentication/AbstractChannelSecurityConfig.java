package com.bobo.security.core.authentication;

import com.bobo.security.core.properties.SecurityConstants;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:32
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    protected AuthenticationSuccessHandler boboAuthenticationSuccessHandler;

    protected AuthenticationFailureHandler boboAuthenticationFailureHandler;

    public AbstractChannelSecurityConfig(AuthenticationSuccessHandler boboAuthenticationSuccessHandler,
                                         AuthenticationFailureHandler boboAuthenticationFailureHandler){
        this.boboAuthenticationFailureHandler = boboAuthenticationFailureHandler;
        this.boboAuthenticationSuccessHandler = boboAuthenticationSuccessHandler;
    }

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(boboAuthenticationSuccessHandler)
                .failureHandler(boboAuthenticationFailureHandler);
    }
}
