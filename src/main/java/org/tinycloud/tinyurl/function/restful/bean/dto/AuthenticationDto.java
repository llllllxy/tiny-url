package org.tinycloud.tinyurl.function.restful.bean.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *     rest服务-租户身份认证
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-27 14:29
 */
@Getter
@Setter
public class AuthenticationDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "授权码不能为空")
    private String authCode;

    @NotEmpty(message = "api访问ak不能为空")
    private String accessKey;

    @NotEmpty(message = "签名不能为空（使用sm3-hmac算法）")
    private String signature;
}
