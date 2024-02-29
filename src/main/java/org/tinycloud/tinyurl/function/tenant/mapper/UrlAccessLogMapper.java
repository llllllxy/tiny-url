package org.tinycloud.tinyurl.function.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantAccessLogQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlAccessLog;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantAccessLogVo;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 19:21
 */
@Repository
public interface UrlAccessLogMapper extends BaseMapper<TUrlAccessLog> {

    IPage<TenantAccessLogVo> pageList(Page page, @Param("queryDto") TenantAccessLogQueryDto queryDto);
}
