package com.bobo.security.core.validata.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/22下午11:53
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
