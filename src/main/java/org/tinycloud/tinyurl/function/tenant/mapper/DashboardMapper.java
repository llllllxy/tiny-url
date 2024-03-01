package org.tinycloud.tinyurl.function.tenant.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> topList(@Param("tenantId") Long tenantId, @Param("today") String today);

    List<Map<String, Object>> countByDateList(@Param("tenantId") Long tenantId, @Param("dayList") List<String> dayList);
}
