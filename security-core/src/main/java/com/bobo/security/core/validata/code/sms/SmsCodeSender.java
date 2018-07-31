/**
 * 
 */
package com.bobo.security.core.validata.code.sms;

/**
 * @author bobo
 *
 */
public interface SmsCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
