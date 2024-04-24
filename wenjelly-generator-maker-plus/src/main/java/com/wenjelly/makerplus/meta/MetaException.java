package com.wenjelly.makerplus.meta;

/*
 * @time 2024/3/10 10:30
 * @package com.wenjelly.makerplus.meta
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

/**
 * 元信息异常类
 */
public class MetaException extends RuntimeException {

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }

}
