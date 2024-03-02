package org.tinycloud.tinyurl.function.tenant.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.tinycloud.tinyurl.common.model.BasePageDto;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-2024/3/2 21:51
 */
@Getter
@Setter
public class StatisticQueryDto extends BasePageDto {

    private String surl;

    private Long tenantId;

    private String today;

    private String month;

    private String yesterday;
}
