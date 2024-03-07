package org.tinycloud.tinyurl.common.config.interceptor.holder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *     登录租户身份权限-BO
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-07 15:53
 */
@Getter
@Setter
public class LoginTenant implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 租户ID
     */
    private Long id;

    /**
     * 租户账号
     */
    private String tenantAccount;

    /**
     * 租户名字
     */
    private String tenantName;

    /**
     * 租户电话
     */
    private String tenantPhone;

    /**
     * 租户邮箱
     */
    private String tenantEmail;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录过期时间
     */
    private Long loginExpireTime;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String ipLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;
}
