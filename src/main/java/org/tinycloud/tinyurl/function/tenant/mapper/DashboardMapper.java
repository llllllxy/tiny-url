package org.tinycloud.tinyurl.function.tenant.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 17:55
 */
@Repository
public interface DashboardMapper {
    Long getAccessTodayQuantity(@Param("tenantId") Long tenantId, @Param("today") String today);

    Long getAccessTodayIpQuantity(@Param("tenantId") Long tenantId, @Param("today") String today);
}
