package org.tinycloud.tinyurl.function.tenant.bean.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-05 15:03
 */
@Data
public class TenantCaptchaCodeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String uuid;

    private String img;

    private String publicKey;
}
