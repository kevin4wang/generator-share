package com.linlihudong.config.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME )
public @interface MethodDesensitization {
    /**
     * 脱敏字段名称
     * @return
     */
    public  String name() default "";

}