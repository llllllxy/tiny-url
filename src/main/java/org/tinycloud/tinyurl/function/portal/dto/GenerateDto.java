package org.tinycloud.tinyurl.function.portal.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-2023/12/1 22:53
 */
@Data
public class GenerateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "原始链接不能为空")
    private String originalUrl;

    @NotEmpty(message = "有效期不能为空")
    private String validityPeriod;
}
