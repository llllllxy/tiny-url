package org.tinycloud.tinyurl.function.tenant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyurl.function.tenant.bean.dto.StatisticQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.StatisticDataVo;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-2024/3/2 22:08
 */
@Repository
public interface StatisticMapper {
    IPage<StatisticDataVo> pageList(Page page, @Param("queryDto") StatisticQueryDto queryDto);
}
