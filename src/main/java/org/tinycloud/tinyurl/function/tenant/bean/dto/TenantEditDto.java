package org.tinycloud.tinyurl.function.tenant.bean.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
 * @since 2023-12-11 14:51
 */
@Getter
@Setter
public class TenantEditDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Length(max = 64, min = 0, message = "租户名称不能超过64个字符")
    @NotEmpty(message = "租户名称不能为空")
    private String tenantName;

    @Length(max = 32, min = 0, message = "手机号不能超过32个字符")
    @NotEmpty(message = "手机号不能为空")
    private String tenantPhone;

    @Length(max = 64, min = 0, message = "电子邮箱不能超过64个字符")
    @NotEmpty(message = "电子邮箱不能为空")
    @Email(message = "请输入正确的电子邮箱地址")
    private String tenantEmail;
}
