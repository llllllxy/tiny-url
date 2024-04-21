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
 * @since 2024-04-2024/4/21 15:08
 */
@Getter
@Setter
public class TenantUrlEditBySurlDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "短链不能为空")
    private String surl;

    @NotEmpty(message = "过期时间不能为空")
    private String expireDate;
}