package org.tinycloud.tinyurl.common.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *   seconds 秒内只能执行 maxCount 次
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-29 09:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 限制周期(秒)
     */
    int seconds();

    /**
     * 规定周期内最大次数
     */
    int maxCount();

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作频率过高，请稍后再试！";
}
