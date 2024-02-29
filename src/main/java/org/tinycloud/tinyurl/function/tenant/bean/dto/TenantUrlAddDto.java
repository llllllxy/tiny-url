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
 * @since 2023-12-2023/12/1 22:53
 */
@Getter
@Setter
public class TenantUrlAddDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "原始链接不能为空")
    private String originalUrl;

    @NotEmpty(message = "过期时间不能为空")
    private String expireDate;

    @NotNull(message = "状态不能为空")
    @Max(value = 1, message = "`状态`参数错误")
    @Min(value = 0, message = "`状态`参数错误")
    private Integer status;
}
