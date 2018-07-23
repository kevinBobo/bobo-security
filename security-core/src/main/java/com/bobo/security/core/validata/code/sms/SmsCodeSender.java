package com.bobo.security.core.validata.code.sms;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:55
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
