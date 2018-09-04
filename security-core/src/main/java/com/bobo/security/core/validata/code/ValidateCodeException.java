/**
 * 
 */
package com.bobo.security.core.validata.code;

import lombok.Data;
import org.springframework.security.core.AuthenticationException;

/**
 * @author bobo
 *
 */
@Data
public class ValidateCodeException extends AuthenticationException {

	public static final ValidateCodeException VALIDATECODE_IS_NULL = new ValidateCodeException(910001," 验证码不能为空");
	public static final ValidateCodeException VALIDATECODE_UN_GET = new ValidateCodeException(910002," 获取验证码的值失败");
	public static final ValidateCodeException VALIDATECODE_UN_HAS = new ValidateCodeException(910003," 验证码不存在");
	public static final ValidateCodeException VALIDATECODE_IS_EXPIRE = new ValidateCodeException(910004," 验证码已过期");
	public static final ValidateCodeException VALIDATECODE_IS_ERROR= new ValidateCodeException(910005," 验证码错误");

	private int code;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

	public ValidateCodeException(int code,String msg) {
		super(msg);
		this.code = code;
	}

}
