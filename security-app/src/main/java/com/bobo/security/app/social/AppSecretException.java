package com.bobo.security.app.social;

import lombok.Data;
import org.springframework.security.core.AuthenticationException;

/**
 * @author bobo
 *
 */
@Data
public class AppSecretException extends AuthenticationException {


	private static final long serialVersionUID = -1629364510827838114L;

	public static final AppSecretException NO_BIND_PHONE = new AppSecretException("无法找到缓存的手机号信息",900001);

	public static final AppSecretException NO_BIND_THIRD = new AppSecretException("无法找到缓存的用户社交账号信息",900002);

	public static final AppSecretException NO_DEVICEID = new AppSecretException("设备id参数不能为空",900003);

	private int code;

	public AppSecretException(String msg){
		super(msg);
	}

	public AppSecretException(String msg, int code) {
		super(msg);
		this.code = code;
	}
}
