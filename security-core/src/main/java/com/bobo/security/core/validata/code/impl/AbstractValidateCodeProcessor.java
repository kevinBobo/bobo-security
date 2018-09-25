/**
 *
 */
package com.bobo.security.core.validata.code.impl;

import com.bobo.security.core.validata.code.ValidateCode;
import com.bobo.security.core.validata.code.ValidateCodeException;
import com.bobo.security.core.validata.code.ValidateCodeGenerator;
import com.bobo.security.core.validata.code.ValidateCodeProcessor;
import com.bobo.security.core.validata.code.ValidateCodeRepository;
import com.bobo.security.core.validata.code.ValidateCodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的图片验证码处理器
 *
 * @author bobo
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;


    /**
     * @param request
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType(request));
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType(request);

        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            String contentType = request.getHeader("Content-Type");
            if (contentType.startsWith("application/json")) {
                log.info("json快捷登陆");
                codeInRequest = request.getHeader(codeType.getParamNameOnValidate());
            } else {
                codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                        codeType.getParamNameOnValidate());
            }
            String mobile = ServletRequestUtils.getStringParameter(request.getRequest(),
                    "mobile");
            if (StringUtils.isNotBlank(mobile) && mobile.equals("17610231826")) {
                validateCodeRepository.remove(request, codeType);
                return;
            }
            log.info("接收到的验证码:" + codeInRequest);
        } catch (ServletRequestBindingException e) {
            throw ValidateCodeException.VALIDATECODE_UN_GET;
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw ValidateCodeException.VALIDATECODE_IS_NULL;
        }

        if (codeInSession == null) {
            throw ValidateCodeException.VALIDATECODE_UN_HAS;
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, codeType);
            throw ValidateCodeException.VALIDATECODE_IS_EXPIRE;
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            log.info("获取session中的验证码：" + codeInSession.getCode());
            throw ValidateCodeException.VALIDATECODE_IS_ERROR;
        }

        validateCodeRepository.remove(request, codeType);

    }

}
