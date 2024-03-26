package com.wenjelly.backend.exception;

/*
 * @time 2024/3/24 14:28
 * @package com.wenjelly.backend.exception
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 * 全局异常处理器
 */

import com.wenjelly.backend.common.BaseResponse;
import com.wenjelly.backend.common.ErrorCode;
import com.wenjelly.backend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}
