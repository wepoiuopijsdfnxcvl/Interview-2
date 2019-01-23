package com.zcy.annotation;

import java.lang.annotation.*;

/**
 * 限制某个ip单位时间内的访问次数（访问频率控制）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
//@Order(Ordered.HIGHEST_PRECEDENCE)// 设置顺序为最高优先级
public @interface LimitRequest {
    
    /**
     * 
     * @Description: 限制某时间段内可以访问的次数，默认设置1
     */
    int limitCounts() default 5;

    /**
     * 
     * @Description: 限制访问的某一个时间段，单位为秒，默认值5秒钟
     */
    int timeSecond() default 1;
    
}