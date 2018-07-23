package com.bobo.security.core.validata.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:17
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
