package org.tinycloud.tinyurl.common.config.filter;

import org.tinycloud.tinyurl.function.tenant.bean.entity.TTenant;

import java.util.Objects;

/**
 * <p>
 *     本地线程变量-缓存restful租户会话信息
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-2024/2/27 21:35
 */
public class ApiFilterHolder {
    private final static ThreadLocal<TTenant> tenant = new ThreadLocal<>();

    public static TTenant getTenant() {
        return tenant.get();
    }

    public static Long getTenantId() {
        TTenant tenant = getTenant();
        if (Objects.isNull(tenant)) {
            return null;
        } else {
            return tenant.getId();
        }
    }

    public static String getTenantAccount() {
        TTenant tenant = getTenant();
        if (Objects.isNull(tenant)) {
            return "";
        } else {
            return tenant.getTenantAccount();
        }
    }

    public static void setTenant(TTenant t) {
        tenant.set(t);
    }

    public static void clearTenant() {
        tenant.remove();
    }
}
