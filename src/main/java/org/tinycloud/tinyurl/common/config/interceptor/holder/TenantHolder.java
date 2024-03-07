package org.tinycloud.tinyurl.common.config.interceptor.holder;

import java.util.Objects;


/**
 * <p>
 * 本地线程变量-缓存租户会话信息
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-2023/12/3 21:20
 */
public class TenantHolder {

    private final static ThreadLocal<LoginTenant> tenant = new ThreadLocal<>();

    public static LoginTenant getTenant() {
        return tenant.get();
    }

    public static Long getTenantId() {
        LoginTenant tenant = getTenant();
        if (Objects.isNull(tenant)) {
            return null;
        } else {
            return tenant.getId();
        }
    }

    public static String getTenantAccount() {
        LoginTenant tenant = getTenant();
        if (Objects.isNull(tenant)) {
            return "";
        } else {
            return tenant.getTenantAccount();
        }
    }

    public static void setTenant(LoginTenant t) {
        tenant.set(t);
    }

    public static void clearTenant() {
        tenant.remove();
    }
}
