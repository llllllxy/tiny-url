package org.tinycloud.tinyurl.function.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.function.tenant.bean.vo.DashboardQuantityVo;
import org.tinycloud.tinyurl.function.tenant.service.DashboardService;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-2024/2/3 22:06
 */
@RestController
@RequestMapping("/tenant/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/quantity")
    public ApiResult<DashboardQuantityVo> quantity() {
        return ApiResult.success(dashboardService.quantity(), "查询成功");
    }
}
