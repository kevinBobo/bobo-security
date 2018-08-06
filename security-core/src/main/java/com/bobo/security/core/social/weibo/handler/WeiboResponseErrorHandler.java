package com.bobo.security.core.social.weibo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.social.support.LoggingErrorHandler;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/4下午12:38
 */
public class WeiboResponseErrorHandler extends LoggingErrorHandler {

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        if (statusCode.value() == 400){
            return false;
        }
        return super.hasError(statusCode);
    }
}
