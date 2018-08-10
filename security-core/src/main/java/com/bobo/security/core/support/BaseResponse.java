package com.bobo.security.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/10上午10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {


    private int code;

    private String message;


}
