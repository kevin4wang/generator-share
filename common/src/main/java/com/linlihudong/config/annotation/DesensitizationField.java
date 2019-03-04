package com.linlihudong.config.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME )
public @interface DesensitizationField {
    /**
     * 脱敏字段名称 name=""
     */
     String name() default "";

}