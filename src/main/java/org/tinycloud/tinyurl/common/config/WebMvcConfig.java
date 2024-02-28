package org.tinycloud.tinyurl.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tinycloud.tinyurl.common.config.interceptor.AccessLimitInterceptor;
import org.tinycloud.tinyurl.common.config.interceptor.TenantAuthInterceptor;
import org.tinycloud.tinyurl.common.config.interceptor.TraceIdInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * WebMvcConfig配置类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 13:02
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TraceIdInterceptor traceIdInterceptor;

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Autowired
    private TenantAuthInterceptor tenantAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> tenantIncludePaths = new ArrayList<>() {{
            add("/tenant/auth/logout");
            add("/tenant/auth/getInfo");
            add("/tenant/auth/initMenu");
            add("/tenant/auth/getTenantInfo");
            add("/tenant/auth/editTenantInfo");
            add("/tenant/auth/getAkInfo");
            add("/tenant/auth/resetAkInfo");
            add("/tenant/auth/editIpSetting");


            add("/tenant/tenant/**"); // 租户信息管理
            add("/tenant/url/**"); // 租户url短链管理
            add("/tenant/accessLog/**"); // 租户短链访问日志管理
            add("/tenant/dashboard/**"); // 租户仪表盘
            add("/tenant/statistic/**"); // 租户数据统计管理
        }};

        // 注册租户会话拦截器
        registry.addInterceptor(tenantAuthInterceptor)
                .addPathPatterns(tenantIncludePaths); // 只拦截这些，其他的都不拦截

        // 注册限流拦截器
        registry.addInterceptor(accessLimitInterceptor)
                .addPathPatterns("/**");

        // 注册MDC拦截器
        registry.addInterceptor(traceIdInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 配置静态资源映射
     *
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/lib/**").addResourceLocations("classpath:/static/lib/");
        registry.addResourceHandler("/page/**").addResourceLocations("classpath:/static/page/");
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/");

        // 配置swagger-ui资源映射(knife4j)
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * 这里加这个配置是为了解决Jackson2ObjectMapperBuilderCustomizer自定义配置不生效的问题
     * 参考自：https://www.jianshu.com/p/09169bd31f72
     */
    @Autowired(required = false)
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        if (Objects.isNull(mappingJackson2HttpMessageConverter)) {
            converters.add(0, new MappingJackson2HttpMessageConverter());
        } else {
            converters.add(0, mappingJackson2HttpMessageConverter);
        }
    }
}
