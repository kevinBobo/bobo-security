/**
 * 
 */
package com.bobo.security.core.validata.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 * @author bobo
 *
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成校验码
	 * @param request
	 * @return
	 */
	ValidateCode generate(ServletWebRequest request);
	
}
