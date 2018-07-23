package com.bobo.security.core.properties;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:04
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();

}
