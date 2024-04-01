package com.wenjelly.springboottemplate.annotation;

/*
 * @time 2024/3/24 14:08
 * @package com.wenjelly.springboottemplate.annotation
 * @project spring-boot-template
 * @author WenJelly
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验权限
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     */
    String mustRole() default "";
}
