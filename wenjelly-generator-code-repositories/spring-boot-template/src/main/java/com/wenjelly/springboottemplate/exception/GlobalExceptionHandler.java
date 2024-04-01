package com.wenjelly.springboottemplate.exception;

/*
 * @time 2024/3/24 14:28
 * @package com.wenjelly.springboottemplate.exception
 * @project spring-boot-template
 * @author WenJelly
 * 全局异常处理器
 */

import com.wenjelly.springboottemplate.common.BaseResponse;
import com.wenjelly.springboottemplate.common.ErrorCode;
import com.wenjelly.springboottemplate.common.ResultUtils;
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
