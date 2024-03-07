package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.common.utils.DateUtils;
import org.tinycloud.tinyurl.function.tenant.bean.dto.StatisticQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.StatisticDataVo;
import org.tinycloud.tinyurl.function.tenant.mapper.StatisticMapper;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-2024/3/2 21:49
 */
@Service
public class StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    public PageModel<StatisticDataVo> query(StatisticQueryDto dto) {
        PageModel<StatisticDataVo> responsePage = new PageModel<>(dto.getPageNo(), dto.getPageSize());
        dto.setTenantId(TenantHolder.getTenantId());
        dto.setToday(DateUtils.today());
        dto.setYesterday(DateUtils.getYesterday());
        dto.setMonth(DateUtils.format(new Date(), "yyyy-MM"));

        IPage<StatisticDataVo> page = this.statisticMapper.pageList(Page.of(dto.getPageNo(), dto.getPageSize()), dto);
        if (page != null && !CollectionUtils.isEmpty(page.getRecords())) {
            responsePage.setTotalPage(page.getPages());
            responsePage.setTotalCount(page.getTotal());
            responsePage.setRecords(page.getRecords());
        }
        return responsePage;
    }
}
