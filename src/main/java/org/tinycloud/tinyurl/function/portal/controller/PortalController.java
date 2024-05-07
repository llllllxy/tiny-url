package org.tinycloud.tinyurl.function.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyurl.common.annotation.AccessLimit;
import org.tinycloud.tinyurl.common.config.ApplicationConfig;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.utils.LocalHostUtils;
import org.tinycloud.tinyurl.common.utils.UrlUtils;
import org.tinycloud.tinyurl.function.portal.dto.GenerateDto;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.service.UrlMapService;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-01 14:28
 */
@Slf4j
@Controller
public class PortalController {

    @Autowired
    private UrlMapService urlMapService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/page/index.html";
    }

    @AccessLimit(seconds = 10, maxCount = 3, msg = "免费用户10秒内只能生成两次短链接")
    @PostMapping("/portal/generate")
    @ResponseBody
    public ApiResult<String> generate(@Validated @RequestBody GenerateDto dto) {
        String originalUrl = dto.getOriginalUrl();
        if (UrlUtils.checkURL(originalUrl)) {
            Date currentDate = new Date();
            Date expireDate = null;
            // 构建过期时间
            if ("sevenday".equals(dto.getValidityPeriod())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.setTime(currentDate);
                // 增加一个周
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                expireDate = calendar.getTime();
            }
            if ("threemonth".equals(dto.getValidityPeriod())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.setTime(currentDate);
                // 增加三个月
                calendar.add(Calendar.MONTH, 3);
                expireDate = calendar.getTime();
            }
            if ("halfyear".equals(dto.getValidityPeriod())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.setTime(currentDate);
                // 增加三个月
                calendar.add(Calendar.MONTH, 6);
                expireDate = calendar.getTime();
            }
            if ("forever".equals(dto.getValidityPeriod())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.setTime(currentDate);
                // 增加三个月
                calendar.add(Calendar.YEAR, 99);
                expireDate = calendar.getTime();
            }
            if (log.isInfoEnabled()) {
                log.info("PortalController -- generate -- expireDate = {}", expireDate);
            }
            TUrlMap tUrlMap = new TUrlMap();
            tUrlMap.setTenantId(1L);
            tUrlMap.setLurl(originalUrl);
            tUrlMap.setDelFlag(GlobalConstant.NOT_DELETED);
            tUrlMap.setStatus(GlobalConstant.ENABLED);
            tUrlMap.setExpireTime(expireDate);
            tUrlMap.setRemark("门户平台生成");
            tUrlMap = urlMapService.generateAndSave(tUrlMap);
            String host = LocalHostUtils.getLocalHost();
            return ApiResult.success(applicationConfig.getAddress() + tUrlMap.getSurl(), "转换短链成功！");
        }
        return ApiResult.fail("请输入正确的网址链接，注意以http://或https://开头");
    }
}
