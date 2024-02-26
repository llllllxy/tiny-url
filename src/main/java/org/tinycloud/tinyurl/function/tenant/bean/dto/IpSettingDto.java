package org.tinycloud.tinyurl.function.tenant.bean.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-26 11:11
 */
@Getter
@Setter
public class IpSettingDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "是否开启IP校验不能为空")
    @Max(value = 1, message = "`是否开启IP校验`参数错误")
    @Min(value = 0, message = "`是否开启IP校验`参数错误")
    private Integer checkIpFlag;

    @Length(max = 255, min = 0, message = "IP访问白名单不能超过255个字符")
    private String ipWhitelist;
}
