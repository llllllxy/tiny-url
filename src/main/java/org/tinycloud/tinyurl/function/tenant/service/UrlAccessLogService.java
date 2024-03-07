package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantAccessLogQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantAccessLogVo;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlAccessLogMapper;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 09:46
 */
@Service
public class UrlAccessLogService {

    @Autowired
    private UrlAccessLogMapper urlAccessLogMapper;

    public PageModel<TenantAccessLogVo> query(TenantAccessLogQueryDto dto) {
        PageModel<TenantAccessLogVo> responsePage = new PageModel<>(dto.getPageNo(), dto.getPageSize());
        dto.setTenantId(TenantHolder.getTenantId());
        if (StrUtils.isNotBlank(dto.getStartTime())) {
            dto.setStartTime(dto.getStartTime() + " 00:00:00");
        }
        if (StrUtils.isNotBlank(dto.getEndTime())) {
            dto.setEndTime(dto.getEndTime() + " 23:59:59");
        }
        IPage<TenantAccessLogVo> urlAccessLogIPage = this.urlAccessLogMapper.pageList(Page.of(dto.getPageNo(), dto.getPageSize()), dto);

        if (urlAccessLogIPage != null && !CollectionUtils.isEmpty(urlAccessLogIPage.getRecords())) {
            responsePage.setTotalPage(urlAccessLogIPage.getPages());
            responsePage.setTotalCount(urlAccessLogIPage.getTotal());
            responsePage.setRecords(urlAccessLogIPage.getRecords());
        }
        return responsePage;
    }

}
