package org.tinycloud.tinyurl.function.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyurl.common.config.filter.ApiFilterHolder;
import org.tinycloud.tinyurl.common.config.interceptor.holder.TenantHolder;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlAddDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlEditBySurlDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlEditDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantUrlVo;
import org.tinycloud.tinyurl.function.tenant.service.UrlMapService;

/**
 * <p>
 * 远程restful操作接口-控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-04 12:44
 */
@RestController
@RequestMapping("/api/url/v1")
public class ApiUrlController {

    @Autowired
    private UrlMapService urlMapService;

    @GetMapping(value = "/detail")
    public ApiResult<TenantUrlVo> detail(@RequestParam(value = "surl") String surl) {
        return ApiResult.success(urlMapService.detailBySurl(surl, ApiFilterHolder.getTenantId()), "获取成功！");
    }

    @GetMapping(value = "/del")
    public ApiResult<Boolean> del(@RequestParam(value = "surl") String surl) {
        return ApiResult.success(urlMapService.delBySurl(surl, ApiFilterHolder.getTenantId()), "删除成功！");
    }

    @GetMapping(value = "/enable")
    public ApiResult<Boolean> enable(@RequestParam(value = "surl") String surl) {
        return ApiResult.success(urlMapService.enableBySurl(surl, ApiFilterHolder.getTenantId()), "启用成功！");
    }

    @GetMapping(value = "/disable")
    public ApiResult<Boolean> disable(@RequestParam(value = "surl") String surl) {
        return ApiResult.success(urlMapService.disableBySurl(surl, ApiFilterHolder.getTenantId()), "停用成功！");
    }

    @PostMapping("/create")
    public ApiResult<Boolean> create(@Validated @RequestBody TenantUrlAddDto dto) {
        return ApiResult.success(urlMapService.add(dto, ApiFilterHolder.getTenantId()), "新增成功");
    }

    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody TenantUrlEditBySurlDto dto) {
        return ApiResult.success(urlMapService.editBySurl(dto, TenantHolder.getTenantId()));
    }
}
