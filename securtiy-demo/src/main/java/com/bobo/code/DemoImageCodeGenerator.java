/**
 * 
 */
package com.bobo.code;

import com.bobo.security.core.validata.code.ValidateCodeGenerator;
import com.bobo.security.core.validata.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author bobo
 *
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	/**
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ImageCode generate(ServletWebRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
