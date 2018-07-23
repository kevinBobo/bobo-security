package com.bobo.security.core.validata.code.image;

import com.bobo.security.core.validata.code.ValidateCodeGenerator;
import com.bobo.security.core.validata.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.util.Map;

/**
 * 图片验证码处理器
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:49
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    public ImageCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators) {
        super(validateCodeGenerators);
    }

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
