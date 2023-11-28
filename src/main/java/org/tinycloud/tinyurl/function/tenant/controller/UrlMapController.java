package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlMapMapper;

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
    private UrlMapMapper UrlMapMapper;

    @GetMapping("/search")
    public ApiResult<?> search() {
        return ApiResult.success(UrlMapMapper.selectList(null));
    }

}
