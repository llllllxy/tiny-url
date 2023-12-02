package org.tinycloud.tinyurl.common.constant;

/**
 * <p>
 *  全局常量类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-03-07 15:47:38
 */
public class GlobalConstant {

    /**
     * 已删除标记
     */
    public static final Integer DELETED = 1;

    /**
     * 未删除标记
     */
    public static final Integer NOT_DELETED = 0;

    /**
     * 正常在用
     */
    public static final Integer ENABLED = 0;

    /**
     * 已停用
     */
    public static final Integer DISABLED = 1;

    /**
     * 限流 redis key
     */
    public static final String LIMIT_REDIS_KEY = "tinyurl:limit:";

    /**
     * 短链缓存 redis key
     */
    public static final String URL_CACHE_REDIS_KEY = "tinyurl:urlcache:";
}
