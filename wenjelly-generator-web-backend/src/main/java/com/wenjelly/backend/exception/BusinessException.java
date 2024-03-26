package com.wenjelly.backend.exception;

/*
 * @time 2024/3/24 14:27
 * @package com.wenjelly.backend.exception
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 自定义异常类
 */

import com.wenjelly.backend.common.ErrorCode;

public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
