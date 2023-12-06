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
 * @since 2023-12-05 14:59
 */
@Getter
@Setter
public class TenantLoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "登录账户不能为空")
    private String username;

    @NotEmpty(message = "登录密码不能为空")
    private String password;

    @NotEmpty(message = "验证码不能为空")
    private String captcha;

    @NotEmpty(message = "唯一键不能为空")
    private String uuid;
}
