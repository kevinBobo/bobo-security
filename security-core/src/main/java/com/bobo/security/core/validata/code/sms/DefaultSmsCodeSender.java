/**
 * 
 */
package com.bobo.security.core.validata.code.sms;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的短信验证码发送器
 * 
 * @author bobo
 *
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {
	

	/**
	 *
	 * @param mobile
	 * @param code
	 */
	@Override
	public void send(String mobile, String code) {
		log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
		log.info("向手机"+mobile+"发送短信验证码"+code);
	}

}
