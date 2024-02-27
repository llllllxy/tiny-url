package org.tinycloud.tinyurl.function.restful.bean.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *     rest服务-签名（测试使用）
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-27 14:29
 */
@Getter
@Setter
public class SignatureDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "授权码不能为空")
    private String authCode;

    @NotEmpty(message = "api访问ak密钥不能为空")
    private String accessKeySecret;
}
