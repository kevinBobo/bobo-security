package com.bobo.security.core.validata.code;

import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.validata.code.image.ImageCodeGenerator;
import com.bobo.security.core.validata.code.sms.DefaultSmsCodeSender;
import com.bobo.security.core.validata.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:54
 */
@Configuration
public class ValidateCodeBeanConfig {

    private SecurityProperties securityProperties;

    public ValidateCodeBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
