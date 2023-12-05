package org.tinycloud.tinyurl.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.tinycloud.tinyurl.common.config.interceptor.ThreadMdcUtils;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池配置类
 *
 * @author liuxingyu01
 * @version 2023-02-22 17:08
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Value("${async.executor.thread.core-pool-size}")
    private int corePoolSize;

    @Value("${async.executor.thread.max-pool-size}")
    private int maxPoolSize;

    @Value("${async.executor.thread.keep-alive-seconds}")
    private int keepAliveSeconds;

    @Value("${async.executor.thread.queue-capacity}")
    private int queueCapacity;

    @Value("${async.executor.thread.name-prefix}")
    private String namePrefix;


    @Bean("asyncServiceExecutor")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        logger.info("start init asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 配置队列大小
        executor.setQueueCapacity(queueCapacity);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);
        // 等待所有任务结果候再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 任务的等待时间 如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);

        // 设置拒绝策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 设置线程装饰器，这里是为了适配 MDC-traceId
        executor.setTaskDecorator(runnable -> ThreadMdcUtils.wrapAsync(runnable, MDC.getCopyOfContextMap()));

        // 执行初始化，初始化 core 线程
        executor.initialize();
        return executor;
    }

}
