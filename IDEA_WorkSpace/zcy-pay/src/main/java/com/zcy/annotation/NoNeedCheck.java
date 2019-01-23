package com.zcy.annotation;

import java.lang.annotation.*;

/**
 * 无需切入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
//@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface NoNeedCheck {
    String value() default "";
}