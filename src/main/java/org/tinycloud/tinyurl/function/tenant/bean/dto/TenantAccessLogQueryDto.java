package org.tinycloud.tinyurl.function.tenant.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.tinycloud.tinyurl.common.model.BasePageDto;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 09:49
 */
@Getter
@Setter
public class TenantAccessLogQueryDto extends BasePageDto {

    private String surl;

    private String startTime;

    private String endTime;

    private String accessIp;

    private Long tenantId;
}
