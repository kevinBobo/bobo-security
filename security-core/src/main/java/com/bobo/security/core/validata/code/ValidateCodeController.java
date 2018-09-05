/**
 *
 */
package com.bobo.security.core.validata.code;

import com.bobo.security.core.properties.SecurityConstants;
import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.support.BaseResponse;
import com.bobo.security.core.support.SimpleResponse;
import com.bobo.security.core.validata.code.sms.codes.CaptchaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 生成校验码的请求处理器
 *
 * @author bobo
 */
@RestController
@Slf4j
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public BaseResponse createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        //如果islimit为true 限流校验
        if (securityProperties.getCode().getSms().isLimit()){
            String phone = ServletRequestUtils.getStringParameter(request,
                    "mobile");
            String captcha = ServletRequestUtils.getStringParameter(request,
                    "captcha");
            if (type.equalsIgnoreCase("sms")) {
                int count = captchaUtil.getSMSCount(phone);
                log.info("获取手机短信验证次数:" + count);
                if (count >= 3) {
                    if (StringUtils.isBlank(captcha)) {
                        Map<String, String> data = captchaUtil.getCaptcha(phone);
                        log.info("发送大于三次，需要文字验证码:" + data);
                        return new BaseResponse(910006, " 需要文字验证码验证", data);
                    }
                    boolean result = captchaUtil.checkCaptchaCode(captcha, phone);
                    //验证文字验证码
                    if (!result) {
                        Map<String, String> data = captchaUtil.getCaptcha(phone);
                        log.info("验证码错误，需要文字验证码:" + data);
                        return new BaseResponse(910006, " 需要文字验证码验证", data);
                    }
                }
            }
        }
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        return new BaseResponse(0, "发送成功");
    }

    @ExceptionHandler(ValidateCodeException.class)
    @ResponseBody
    public Object exp(ValidateCodeException ex) {
            log.error("阿里云发送失败");
            return new BaseResponse(ex.getCode(),ex.getMessage());
    }

}
