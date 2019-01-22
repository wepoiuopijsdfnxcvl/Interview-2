package com.zcy.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 权限请求头限制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)        // 设置顺序为最高优先级
public @interface AuthorizationRequest {

    String value() default "";
    
}