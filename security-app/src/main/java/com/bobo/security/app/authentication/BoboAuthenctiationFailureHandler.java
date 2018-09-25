/**
 * 
 */
package com.bobo.security.app.authentication;

import com.bobo.security.app.social.AppSecretException;
import com.bobo.security.core.support.BaseResponse;
import com.bobo.security.core.support.SimpleResponse;
import com.bobo.security.core.validata.code.ValidateCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * APP环境下认证失败处理器
 * 
 * @author bobo
 *
 */
@Component("boboAuthenctiationFailureHandler")
@Slf4j
public class BoboAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


	@Autowired
	private ObjectMapper objectMapper;

	/**
	 *
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		
		log.info("登录失败");
		response.setContentType("application/json;charset=UTF-8");

		if(exception instanceof AppSecretException){
			response.setStatus(HttpStatus.OK.value());
			AppSecretException e = (AppSecretException) exception;
			response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse(e.getCode(),e.getMessage())));
		}else if (exception instanceof ValidateCodeException){
			response.setStatus(HttpStatus.OK.value());
			ValidateCodeException e = (ValidateCodeException) exception;
			response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse(e.getCode(),e.getMessage())));
		}else {
			exception.printStackTrace();
			response.setStatus(HttpStatus.OK.value());
			response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse(900000,exception.getMessage())));
		}


	}

}
