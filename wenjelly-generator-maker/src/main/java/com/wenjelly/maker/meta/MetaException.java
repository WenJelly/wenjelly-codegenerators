package com.wenjelly.maker.meta;

/*
 * @time 2024/3/10 10:30
 * @package com.wenjelly.maker.meta
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

public class MetaException extends RuntimeException{

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause){
        super(message,cause);
    }

}
