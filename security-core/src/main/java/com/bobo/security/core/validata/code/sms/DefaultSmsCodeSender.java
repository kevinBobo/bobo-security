/**
 * 
 */
package com.bobo.security.core.validata.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的短信验证码发送器
 * 
 * @author bobo
 *
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 *
	 * @param mobile
	 * @param code
	 */
	@Override
	public void send(String mobile, String code) {
		logger.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
		logger.info("向手机"+mobile+"发送短信验证码"+code);
	}

}
