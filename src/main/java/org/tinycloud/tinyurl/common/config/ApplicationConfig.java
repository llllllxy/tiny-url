package org.tinycloud.tinyurl.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     映射项目配置为配置类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-05 15:29
 */
@Component
@ConfigurationProperties(prefix = "tinyurl")
public class ApplicationConfig {

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统版本
     */
    private String version;

    /**
     * 系统数据库类型
     */
    private String dbType = "mysql";

    /**
     * 租户系统会话时长
     */
    private Integer tenantAuthTimeout = 1800;

    /**
     * 管理系统会话时长
     */
    private Integer adminAuthTimeout = 1800;

    /**
     * restful-api系统会话时长
     */
    private Integer apiAuthTimeout = 1800;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Integer getTenantAuthTimeout() {
        return tenantAuthTimeout;
    }

    public void setTenantAuthTimeout(Integer tenantAuthTimeout) {
        this.tenantAuthTimeout = tenantAuthTimeout;
    }

    public Integer getAdminAuthTimeout() {
        return adminAuthTimeout;
    }

    public void setAdminAuthTimeout(Integer adminAuthTimeout) {
        this.adminAuthTimeout = adminAuthTimeout;
    }

    public Integer getApiAuthTimeout() {
        return apiAuthTimeout;
    }

    public void setApiAuthTimeout(Integer apiAuthTimeout) {
        this.apiAuthTimeout = apiAuthTimeout;
    }
}
