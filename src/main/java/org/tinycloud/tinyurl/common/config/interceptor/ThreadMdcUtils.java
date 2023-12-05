package org.tinycloud.tinyurl.common.config.interceptor;

import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * MDC-traceId 异步调用配置，解决异步任务必然是获取不到 traceId 的问题，需要在线程池配置中手动添加
 * </p>
 * 参考自 https://juejin.cn/post/7137187959580655646
 *
 * @author liuxingyu01
 * @since 2023-12-05 09:42
 */
public class ThreadMdcUtils {
    public static Runnable wrapAsync(Runnable task, Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            if (MDC.get("traceId") == null) {
                MDC.put("traceId", UUID.randomUUID().toString().replace("-", ""));
            }
            try {
                task.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
