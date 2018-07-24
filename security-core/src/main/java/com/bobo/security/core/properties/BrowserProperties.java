package com.bobo.security.core.properties;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/22下午9:39
 */
@Data
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String signUpUrl = "/bobo-signUp.html";

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginResponseType loginType = LoginResponseType.JSON;

    private int rememberMeSeconds = 3600;
}
