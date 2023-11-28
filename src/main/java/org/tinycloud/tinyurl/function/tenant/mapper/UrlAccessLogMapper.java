package org.tinycloud.tinyurl.function.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlAccessLog;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 19:21
 */
@Repository
public interface UrlAccessLogMapper extends BaseMapper<TUrlAccessLog> {
}
