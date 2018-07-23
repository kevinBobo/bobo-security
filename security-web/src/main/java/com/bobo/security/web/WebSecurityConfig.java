package com.bobo.security.web;

import com.bobo.security.core.authentication.AbstractChannelSecurityConfig;
import com.bobo.security.core.authentication.moblie.SmsCodeAuthenticationSecurityConfig;
import com.bobo.security.core.properties.SecurityConstants;
import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.validata.code.ValidateCodeSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/21下午10:21
 */
@Configuration
@Slf4j
public class WebSecurityConfig extends AbstractChannelSecurityConfig {

    private SecurityProperties securityProperties;

    private DataSource dataSource;

    private UserDetailsService userDetailsService;

    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    private SpringSocialConfigurer imoocSocialSecurityConfig;

    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    private InvalidSessionStrategy invalidSessionStrategy;

    public WebSecurityConfig(AuthenticationSuccessHandler boboAuthenticationSuccessHandler
            , AuthenticationFailureHandler boboAuthenticationFailureHandler
            , SecurityProperties securityProperties, DataSource dataSource
            , UserDetailsService userDetailsService
            , SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig
            , ValidateCodeSecurityConfig validateCodeSecurityConfig
            , SpringSocialConfigurer imoocSocialSecurityConfig
            , SessionInformationExpiredStrategy sessionInformationExpiredStrategy
            , InvalidSessionStrategy invalidSessionStrategy) {
        super(boboAuthenticationSuccessHandler, boboAuthenticationFailureHandler);
        this.securityProperties = securityProperties;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.imoocSocialSecurityConfig = imoocSocialSecurityConfig;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
        this.invalidSessionStrategy = invalidSessionStrategy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(imoocSocialSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
                        "/user/regist")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        try {
            tokenRepository.setCreateTableOnStartup(true);
        }catch (Exception e){
            log.info("remeberme is already existed");
        }
        return tokenRepository;
    }
}
