package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlAddDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlEditDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantUrlVo;
import org.tinycloud.tinyurl.function.tenant.service.UrlMapService;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 14:28
 */
@Slf4j
@RestController
@RequestMapping("/tenant/url")
public class UrlMapController {

    @Autowired
    private UrlMapService urlMapService;

    @PostMapping("/query")
    public ApiResult<PageModel<TenantUrlVo>> query(@Validated @RequestBody TenantUrlQueryDto dto) {
        return ApiResult.success(urlMapService.query(dto, TenantHolder.getTenantId()), "查询成功！");
    }

    @GetMapping(value = "/del")
    public ApiResult<Boolean> del(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.del(id, TenantHolder.getTenantId()), "删除成功！");
    }

    @GetMapping(value = "/enable")
    public ApiResult<Boolean> enable(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.enable(id, TenantHolder.getTenantId()), "启用成功！");
    }

    @GetMapping(value = "/disable")
    public ApiResult<Boolean> disable(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.disable(id, TenantHolder.getTenantId()), "停用成功！");
    }

    @PostMapping("/add")
    public ApiResult<Boolean> add(@Validated @RequestBody TenantUrlAddDto dto) {
        return ApiResult.success(urlMapService.add(dto, TenantHolder.getTenantId()));
    }


    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody TenantUrlEditDto dto) {
        return ApiResult.success(urlMapService.edit(dto, TenantHolder.getTenantId()));
    }
}
