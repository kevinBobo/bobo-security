/**
 * 
 */
package com.bobo.security.core.validata.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author bobo
 *
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
