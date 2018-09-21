package com.bobo.security.web.authentication;

import com.bobo.security.core.properties.LoginResponseType;
import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.support.BaseResponse;
import com.bobo.security.core.support.SimpleResponse;
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
 * @author bobo
 * @Description:
 * @date 2018/7/22下午10:28
 */
@Slf4j
@Component("boboAuthenticationFailureHandler")
public class BoboAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {


        logger.info("登录失败");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse(900008,"账户名或密码错误",exception.getMessage())));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
