package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyurl.common.config.interceptor.TenantHolder;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.utils.DateUtils;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlAccessLog;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.bean.vo.DashboardQuantityVo;
import org.tinycloud.tinyurl.function.tenant.mapper.DashboardMapper;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlAccessLogMapper;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlMapMapper;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 17:22
 */
@Service
public class DashboardService {

    @Autowired
    private UrlMapMapper urlMapMapper;

    @Autowired
    private UrlAccessLogMapper urlAccessLogMapper;

    @Autowired
    private DashboardMapper dashboardMapper;

    public DashboardQuantityVo quantity() {
        DashboardQuantityVo dashboardQuantityVo = new DashboardQuantityVo();
        Long tenantId = TenantHolder.getTenantId();
        String today = DateUtils.today();
        Long urlQuantity = this.urlMapMapper.selectCount(Wrappers.<TUrlMap>lambdaQuery()
                .eq(TUrlMap::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(TUrlMap::getTenantId, tenantId));
        Long accessQuantity = this.urlAccessLogMapper.selectCount(Wrappers.<TUrlAccessLog>lambdaQuery()
                .eq(TUrlAccessLog::getTenantId, tenantId));
        Long todayAccessQuantity = this.dashboardMapper.getAccessTodayQuantity(tenantId, today);
        Long todayIpQuantity = this.dashboardMapper.getAccessTodayIpQuantity(tenantId, today);

        dashboardQuantityVo.setUrlQuantity(urlQuantity);
        dashboardQuantityVo.setAccessQuantity(accessQuantity);
        dashboardQuantityVo.setTodayAccessQuantity(todayAccessQuantity);
        dashboardQuantityVo.setTodayIpQuantity(todayIpQuantity);
        return dashboardQuantityVo;
    }
}
