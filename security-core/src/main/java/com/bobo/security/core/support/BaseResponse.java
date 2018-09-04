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
public class BaseResponse<T> {

    public final static BaseResponse SUCCESS = new BaseResponse(0,"操作成功");

    private int code;

    private String message;

    private T data;

    public BaseResponse(int code,String message){
        this.code = code;
        this.message = message;
    }


}
