package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.annotation.AccessLimit;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
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
