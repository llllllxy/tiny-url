package org.tinycloud.tinyurl.function.tenant.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.tinycloud.tinyurl.common.model.BasePageDto;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-2024/2/3 22:13
 */
@Getter
@Setter
public class TenantUrlQueryDto extends BasePageDto {

    private String surl;

    private String createdAt;

    private Integer status;
}
