package org.tinycloud.tinyurl.function.tenant.bean.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-04 15:03
 */
@Getter
@Setter
public class TenantRegisterDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;

    @NotEmpty(message = "组织团队账号不能为空")
    private String tenantAccount;

    @NotEmpty(message = "组织团队名称不能为空")
    private String tenantName;

    @NotEmpty(message = "登录密码不能为空")
    private String password;

    @NotEmpty(message = "邮箱不能为空")
    private String tenantEmail;

    @NotEmpty(message = "邮箱验证码不能为空")
    private String emailCode;

    @NotEmpty(message = "唯一键不能为空")
    private String uuid;
}
