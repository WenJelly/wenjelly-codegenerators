package com.wenjelly.springboottemplate.common;

/*
 * @time 2024/3/24 14:12
 * @package com.wenjelly.springboottemplate.common
 * @project spring-boot-template
 * @author WenJelly
 * 通用返回类
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
