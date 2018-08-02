/**
 * 
 */
package com.bobo.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信登录配置项
 * 
 * @author bobo
 *
 */
@Setter
@Getter
public class WeixinProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";


	

}
