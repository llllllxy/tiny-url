package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.utils.DateUtils;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlAccessLog;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.bean.vo.DashboardQuantityVo;
import org.tinycloud.tinyurl.function.tenant.mapper.DashboardMapper;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlAccessLogMapper;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlMapMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 17:22
 */
@Slf4j
@Service
public class DashboardService {

    @Autowired
    private UrlMapMapper urlMapMapper;

    @Autowired
    private UrlAccessLogMapper urlAccessLogMapper;

    @Autowired
    private DashboardMapper dashboardMapper;

    public DashboardQuantityVo quantity() {
        Long tenantId = TenantHolder.getTenantId();
        String today = DateUtils.today();
        Long urlQuantity = this.urlMapMapper.selectCount(Wrappers.<TUrlMap>lambdaQuery()
                .eq(TUrlMap::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(TUrlMap::getTenantId, tenantId));
        Long accessQuantity = this.urlAccessLogMapper.selectCount(Wrappers.<TUrlAccessLog>lambdaQuery()
                .eq(TUrlAccessLog::getTenantId, tenantId));
        Long todayAccessQuantity = this.dashboardMapper.getAccessTodayQuantity(tenantId, today);
        Long todayIpQuantity = this.dashboardMapper.getAccessTodayIpQuantity(tenantId, today);
        DashboardQuantityVo dashboardQuantityVo = new DashboardQuantityVo();
        dashboardQuantityVo.setUrlQuantity(urlQuantity);
        dashboardQuantityVo.setAccessQuantity(accessQuantity);
        dashboardQuantityVo.setTodayAccessQuantity(todayAccessQuantity);
        dashboardQuantityVo.setTodayIpQuantity(todayIpQuantity);
        return dashboardQuantityVo;
    }

    public List<Map<String, Object>> topList() {
        Long tenantId = TenantHolder.getTenantId();
        String today = DateUtils.today();
        return this.dashboardMapper.topList(tenantId, today);
    }

    public Map<String, Object> chartsInfo() {
        Map<String, Object> result = new HashMap<>();
        Long tenantId = TenantHolder.getTenantId();

        List<String> dayList = new ArrayList<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 格式化输出的日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dayList.add(currentDate.format(formatter));
        // 获取前七天的日期并格式化输出
        for (int i = 1; i < 7; i++) {
            LocalDate previousDate = currentDate.minusDays(i);
            dayList.add(previousDate.format(formatter));
        }
        // 倒序 ArrayList
        Collections.reverse(dayList);
        result.put("dayList", dayList);
        List<Map<String, Object>> list = this.dashboardMapper.countByDateList(tenantId, dayList);
        List<Long> dataList = list.stream().map(item -> {
            return Long.parseLong(item.get("accessCount").toString());
        }).collect(Collectors.toList());
        result.put("dataList", dataList);
        return result;
    }
}
