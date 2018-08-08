/**
 * 
 */
package com.bobo.security.server;

import com.bobo.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.bobo.security.core.authentication.FormAuthenticationConfig;
import com.bobo.security.core.authentication.moblie.SmsCodeAuthenticationSecurityConfig;
import com.bobo.security.core.authorize.AuthorizeConfigManager;
import com.bobo.security.core.properties.SecurityConstants;
import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.validata.code.ValidateCodeSecurityConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器配置
 * 
 * @author bobo
 *
 */
@Configuration
@EnableResourceServer
public class BoboResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	protected AuthenticationSuccessHandler boboAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler boboAuthenticationFailureHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer boboSocialSecurityConfig;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		formAuthenticationConfig.configure(http);
		
		http.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.apply(boboSocialSecurityConfig)
				.and()
			.apply(openIdAuthenticationSecurityConfig)
				.and()
			.csrf().disable();

		http.authorizeRequests().antMatchers(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL).permitAll();

		if (StringUtils.isNotBlank(securityProperties.getNoAuthUrl().getUrl())){
			http.authorizeRequests().antMatchers(securityProperties.getNoAuthUrl().getUrl()).permitAll();
		}

		authorizeConfigManager.config(http.authorizeRequests());
	}
	
}