/**
 * 
 */
package com.bobo.security;

import com.bobo.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author bobo
 *
 */
@Component
public class DemoAuthorizeConifgProvider implements AuthorizeConfigProvider {

	/**
	 *
	 * @param config
	 * @return
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		//demo项目授权配置
		return false;
	}

}
