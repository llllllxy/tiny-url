package org.tinycloud.tinyurl.common.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.tinycloud.tinyurl.common.annotation.AccessLimit;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.enums.CommonCode;
import org.tinycloud.tinyurl.common.exception.BusinessException;
import org.tinycloud.tinyurl.common.utils.IpGetUtils;
import org.tinycloud.tinyurl.common.utils.JsonUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-29 09:35
 */
@Slf4j
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 判断请求类型，如果是OPTIONS，直接返回
            String options = HttpMethod.OPTIONS.toString();
            if (options.equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            // 接口上没有注解，说明这个接口不做限制
            if (accessLimit == null) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String ip = IpGetUtils.getIpAddr(request);
            String method = request.getMethod();
            String requestURI = request.getRequestURI().replace("/", "_");

            String redisKey = GlobalConstant.LIMIT_REDIS_KEY + ip + ":" + method + ":" + requestURI;
            Object redisResult = redisTemplate.opsForValue().get(redisKey);
            // 获取当前访问次数
            Integer count = JsonUtils.convertValue(redisResult, Integer.class);
            if (count == null) {
                // 在规定周期内第一次访问，存入redis，次数+1
                redisTemplate.opsForValue().increment(redisKey, 1);
                redisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            } else {
                if (count >= maxCount) {
                    throw new BusinessException(CommonCode.DOING_IT_TOO_FAST.getCode(), accessLimit.msg());
                } else {
                    // 没超出访问限制次数，则继续次数+1
                    redisTemplate.opsForValue().increment(redisKey, 1);
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
