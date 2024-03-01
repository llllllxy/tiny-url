package org.tinycloud.tinyurl.function.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantAccessLogQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantAccessLogVo;
import org.tinycloud.tinyurl.function.tenant.service.UrlAccessLogService;

/**
 * <p>
 *     访问日志-控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-2024/2/28 22:12
 */
@RestController
@RequestMapping("/tenant/accessLog")
public class AccessLogController {

    @Autowired
    private UrlAccessLogService urlAccessLogService;

    @PostMapping("/query")
    public ApiResult<PageModel<TenantAccessLogVo>> query(@Validated @RequestBody TenantAccessLogQueryDto dto) {
        return ApiResult.success(urlAccessLogService.query(dto), "查询成功！");
    }
}
