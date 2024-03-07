package org.tinycloud.tinyurl.common.config.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tinycloud.tinyurl.common.config.ApplicationConfig;
import org.tinycloud.tinyurl.common.config.interceptor.holder.LoginTenant;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.enums.TenantErrorCode;
import org.tinycloud.tinyurl.common.exception.TenantException;
import org.tinycloud.tinyurl.common.utils.JsonUtils;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TTenant;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2022-03-26 20:48
 * @description 租户会话拦截器
 **/
@Slf4j
@Component
public class TenantAuthInterceptor implements HandlerInterceptor {

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ApplicationConfig applicationConfig;

    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 判断请求类型，如果是OPTIONS，直接返回
        String options = HttpMethod.OPTIONS.toString();
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 先判断token是否为空
        String token = TenantAuthUtil.getToken(request);
        if (StrUtils.isBlank(token)) {
            throw new TenantException(TenantErrorCode.TENANT_NOT_LOGIN);
        }
        // 再判断token是否存在
        String tenantInfoString = redisTemplate.opsForValue().get(GlobalConstant.TENANT_TOKEN_REDIS_KEY + token);
        if (StrUtils.isBlank(tenantInfoString)) {
            throw new TenantException(TenantErrorCode.TENANT_NOT_LOGIN);
        }

        // 再判断token是否合法
        LoginTenant loginTenant = JsonUtils.readValue(tenantInfoString, LoginTenant.class);
        if (Objects.isNull(loginTenant)) {
            throw new TenantException(TenantErrorCode.TENANT_NOT_LOGIN);
        }
        long expireTime = loginTenant.getLoginExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            // 刷新会话缓存时长
            loginTenant.setLoginExpireTime(currentTime + applicationConfig.getTenantAuthTimeout() * 1000);
            redisTemplate.expire(GlobalConstant.TENANT_TOKEN_REDIS_KEY + token, applicationConfig.getTenantAuthTimeout(), TimeUnit.SECONDS);
        }

        TenantHolder.setTenant(loginTenant);

        // 合格不需要拦截，放行
        return true;
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        TenantHolder.clearTenant();
    }

}