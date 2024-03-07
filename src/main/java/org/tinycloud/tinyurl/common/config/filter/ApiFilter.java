package org.tinycloud.tinyurl.common.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.tinycloud.tinyurl.common.config.ApplicationConfig;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.enums.RestfulErrorCode;
import org.tinycloud.tinyurl.common.exception.RestfulException;
import org.tinycloud.tinyurl.common.utils.IpGetUtils;
import org.tinycloud.tinyurl.common.utils.JsonUtils;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TTenant;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * rest服务开放接口鉴权过滤器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-04 11:44
 */
@Component
public class ApiFilter extends OncePerRequestFilter implements Ordered {
    final static Logger log = LoggerFactory.getLogger(ApiFilter.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (log.isInfoEnabled()) {
            log.info("ApiFilter -- doFilterInternal -- start = {}", request.getRequestURI());
        }
        try {
            String token = request.getHeader(GlobalConstant.TENANT_RESTFUL_TOKEN_KEY);
            if (StrUtils.isBlank(token)) {
                handlerExceptionResolver.resolveException(request, response, null, new RestfulException(RestfulErrorCode.TOKEN_CAN_NOT_BE_NULL));
                return;
            }
            // 再判断token是否存在
            String tenantInfoString = stringRedisTemplate.opsForValue().get(GlobalConstant.TENANT_RESTFUL_TOKEN_REDIS_KEY + token);
            if (StrUtils.isBlank(tenantInfoString)) {
                handlerExceptionResolver.resolveException(request, response, null, new RestfulException(RestfulErrorCode.RESTFUL_IS_NOT_LOGIN));
                return;
            }

            // 再判断token是否合法
            TTenant tenantInfo = JsonUtils.readValue(tenantInfoString, TTenant.class);
            if (Objects.isNull(tenantInfo)) {
                handlerExceptionResolver.resolveException(request, response, null, new RestfulException(RestfulErrorCode.RESTFUL_IS_NOT_LOGIN));
                return;
            }

            // 白名单校验
            Integer checkIpFlag = tenantInfo.getCheckIpFlag();
            if (checkIpFlag == 1) {
                String ipWhitelist = tenantInfo.getIpWhitelist();
                if (StrUtils.isNotBlank(ipWhitelist)) {
                    if (!ipWhitelist.contains(IpGetUtils.getIpAddr(request))) {
                        handlerExceptionResolver.resolveException(request, response, null, new RestfulException(RestfulErrorCode.IP_IS_NOT_IN_WHITELIST));
                        return;
                    }
                }
            }

            // 刷新会话缓存时长
            stringRedisTemplate.expire(GlobalConstant.TENANT_RESTFUL_TOKEN_REDIS_KEY + token, applicationConfig.getApiAuthTimeout(), TimeUnit.SECONDS);

            ApiFilterHolder.setTenant(tenantInfo);
            // 进行过滤操作
            chain.doFilter(request, response);
        } finally {
            // ThreadLocal用完一定要remove
            ApiFilterHolder.clearTenant();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 配置放行规则
        String uri = request.getRequestURI();
        // 只拦截 /api/url/** 路径的，后续有需要可以在这里再添加
        return !matchPath(List.of("/api/url/**"), uri);
    }

    /**
     * 匹配 配置的路径和当前请求路径（支持Spring的通配符 '{}','*','**','?'）
     *
     * @param configPaths 配置路径列表
     * @param requestPath 当前请求路径
     * @return true匹配成功，反则反之
     */
    private boolean matchPath(List<String> configPaths, String requestPath) {
        if (CollectionUtils.isEmpty(configPaths) || StrUtils.isBlank(requestPath)) {
            return false;
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String configPath : configPaths) {
            boolean isPattern = pathMatcher.isPattern(configPath);
            if (isPattern) {
                if (pathMatcher.match(configPath, requestPath)) {
                    return true;
                }
            } else {
                if (configPath.equals(requestPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 1; // 指定顺序值为1
    }
}
