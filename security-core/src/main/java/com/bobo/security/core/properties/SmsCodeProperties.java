package com.bobo.security.core.properties;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:34
 */
@Data
public class SmsCodeProperties {

    private int length = 6;
    private int expireIn = 60;

    private String url;
}
