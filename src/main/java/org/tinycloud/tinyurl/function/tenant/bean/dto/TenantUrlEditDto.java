package org.tinycloud.tinyurl.function.tenant.bean.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *     短链新增dto
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 15:34
 */
@Getter
@Setter
public class TenantUrlEditDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空")
    private Long id;

    @NotEmpty(message = "过期时间不能为空")
    private String expireDate;
}