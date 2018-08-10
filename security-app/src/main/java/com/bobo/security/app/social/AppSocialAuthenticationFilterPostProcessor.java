package com.bobo.security.app.social;

import com.bobo.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/7下午6:21
 */
@Component
@Slf4j
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler boboAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler boboAuthenticationFailureHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(boboAuthenticationSuccessHandler);
        socialAuthenticationFilter.setAuthenticationFailureHandler(boboAuthenticationFailureHandler);
    }
}
