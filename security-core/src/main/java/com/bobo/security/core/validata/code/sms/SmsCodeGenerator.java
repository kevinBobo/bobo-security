package com.bobo.security.core.validata.code.sms;

import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.validata.code.ValidateCode;
import com.bobo.security.core.validata.code.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:58
 */
@Component("smsValidateCodeGenerator")
@Data
public class SmsCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    public SmsCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

}
