package com.bobo.security.core.properties;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午8:02
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;
}
