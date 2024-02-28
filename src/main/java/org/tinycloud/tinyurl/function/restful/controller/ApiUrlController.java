package org.tinycloud.tinyurl.function.restful.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.model.ApiResult;

/**
 * <p>
 *     远程restful操作接口-控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-04 12:44
 */
@RestController
@RequestMapping("/api/url/v1")
public class ApiUrlController {

    @GetMapping("/test")
    public ApiResult<?> test() {
        return ApiResult.success("测试调用成功");
    }
}
