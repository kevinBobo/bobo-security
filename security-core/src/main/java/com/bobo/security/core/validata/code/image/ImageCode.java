package com.bobo.security.core.validata.code.image;

import com.bobo.security.core.validata.code.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/22下午11:35
 */
@Data
public class ImageCode extends ValidateCode {

    public ImageCode(BufferedImage image,String code,int expireInt){
        super(code,expireInt);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }

    private BufferedImage image;

}
