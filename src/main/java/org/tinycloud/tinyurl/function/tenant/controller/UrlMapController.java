package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyurl.common.annotation.AccessLimit;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantLoginDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantUrlQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantUrlVo;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlMapMapper;
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
        return ApiResult.success(urlMapService.query(dto), "查询成功！");
    }

    @GetMapping(value = "/del")
    public ApiResult<Boolean> del(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.del(id), "删除成功！");
    }

    @GetMapping(value = "/enable")
    public ApiResult<Boolean> enable(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.enable(id), "启用成功！");
    }

    @GetMapping(value = "/disable")
    public ApiResult<Boolean> disable(@RequestParam(value = "id") Long id) {
        return ApiResult.success(urlMapService.disable(id), "停用成功！");
    }


    @GetMapping("/insert")
    public ApiResult<?> insert() {
        TUrlMap urlMap = new TUrlMap();
        urlMap.setLurl("https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10");
        urlMap.setTenantId(1L);
        urlMap.setDelFlag(0);
        urlMap.setStatus(0);

        return ApiResult.success(urlMapService.generateAndSave(urlMap));
    }
}
