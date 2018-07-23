package com.bobo.security.web;

import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.web.session.BoboExpiredSessionStrategy;
import com.bobo.security.web.session.BoboInvalidSessionStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:28
 */
@Configuration
public class BrowserSecurityBeanConfig {

    private SecurityProperties securityProperties;

    public BrowserSecurityBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new BoboInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new BoboExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }
}
