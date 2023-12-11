package org.tinycloud.tinyurl.function.tenant.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantEditDto;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantLoginDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantCaptchaCodeVo;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantInfoVo;
import org.tinycloud.tinyurl.function.tenant.service.TenantAuthService;

import java.util.*;


/**
 * <p>
 * 租户会话管理-控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-2023/12/3 21:03
 */
@Slf4j
@RestController
@RequestMapping("/tenant/auth")
public class TenantAuthController {

    @Autowired
    private TenantAuthService tenantAuthService;

    @GetMapping("/getCode")
    @ResponseBody
    public ApiResult<TenantCaptchaCodeVo> getCode() {
        return ApiResult.success(tenantAuthService.getCode(), "获取验证码成功");
    }

    @PostMapping("/login")
    public ApiResult<String> login(@Validated @RequestBody TenantLoginDto dto) {
        return ApiResult.success(tenantAuthService.login(dto), "登录成功，欢迎回来！");
    }

    @GetMapping("/logout")
    public ApiResult<Boolean> logout(HttpServletRequest request) {
        return ApiResult.success(tenantAuthService.logout(request), "退出登录成功！");
    }

    @GetMapping("/initMenu")
    public ApiResult<?> initMenu() {
        Map<String, Object> initInfo = new HashMap<>();

        Map<String, String> menuItem1 = new HashMap<>();
        menuItem1.put("title", "短链管理");
        menuItem1.put("href", "page/tenant/url.html");
        menuItem1.put("icon", "fa fa-navicon");
        menuItem1.put("target", "_self");

        Map<String, String> menuItem2 = new HashMap<>();
        menuItem2.put("title", "访问日志");
        menuItem2.put("href", "page/tenant/accesslog.html");
        menuItem2.put("icon", "fa fa-navicon");
        menuItem2.put("target", "_self");

        Map<String, String> menuItem3 = new HashMap<>();
        menuItem3.put("title", "数据统计");
        menuItem3.put("href", "page/tenant/statistic.html");
        menuItem3.put("icon", "fa fa-navicon");
        menuItem3.put("target", "_self");

        List<Map<String, String>> menuList = new ArrayList<>();
        menuList.add(menuItem1);
        menuList.add(menuItem2);
        menuList.add(menuItem3);

        Map<String, String> homeInfo = new HashMap<>();
        homeInfo.put("title", "首页");
        homeInfo.put("href", "page/tenant/dashboard.html");

        Map<String, String> logoInfo = new HashMap<>();
        logoInfo.put("title", "租户控制台");
        logoInfo.put("image", "/images/logo.png");
        logoInfo.put("href", "");

        initInfo.put("homeInfo", homeInfo);
        initInfo.put("logoInfo", logoInfo);
        initInfo.put("menuInfo", menuList);

        return ApiResult.success(initInfo, "获取成功");
    }

    @GetMapping("/getTenantInfo")
    public ApiResult<TenantInfoVo> getTenantInfo() {
        return ApiResult.success(tenantAuthService.getTenantInfo(), "获取租户信息成功，欢迎回来！");
    }

    @PostMapping("/editTenantInfo")
    public ApiResult<Boolean> editTenantInfo(@Validated @RequestBody TenantEditDto dto) {
        return ApiResult.success(tenantAuthService.editTenantInfo(dto), "修改租户信息成功，欢迎回来！");
    }

    @GetMapping("/getAkInfo")
    public ApiResult<TenantInfoVo> getAkInfo() {
        return ApiResult.success(tenantAuthService.getAkInfo(), "获取租户密钥成功，欢迎回来！");
    }
}
