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
 * @since 2024-04-11 14:54
 */
@Getter
@Setter
public class TenantEditPasswordDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "旧的登录密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新的登录密码不能为空")
    private String newPassword;

    @NotEmpty(message = "二次输入的新的登录密码不能为空")
    private String againPassword;

    @NotEmpty(message = "验证码不能为空")
    private String captcha;

    @NotEmpty(message = "唯一键不能为空")
    private String uuid;
}
