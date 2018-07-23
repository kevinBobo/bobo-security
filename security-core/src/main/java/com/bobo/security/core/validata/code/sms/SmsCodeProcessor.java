package com.bobo.security.core.validata.code.sms;

import com.bobo.security.core.properties.SecurityConstants;
import com.bobo.security.core.validata.code.ValidateCode;
import com.bobo.security.core.validata.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:59
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    private SmsCodeSender smsCodeSender;

    public SmsCodeProcessor(SmsCodeSender smsCodeSender) {
        this.smsCodeSender = smsCodeSender;
    }

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
