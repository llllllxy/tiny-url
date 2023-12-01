package org.tinycloud.tinyurl.function.core.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.service.UrlMapService;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 短链解析转发
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-29 14:53
 */
@Slf4j
@Controller
public class CoreController {

    @Autowired
    private UrlMapService urlMapService;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/page/index.html";
    }

    @GetMapping("/{surl}")
    public String redirect(@PathVariable("surl") String surl, HttpServletRequest request) {
        // 根据断链，获取原始url
        TUrlMap urlDataMap = urlMapService.getUrlMapBySurl(surl);
        // 没有对应的原始链接，则直接返回404页
        if (Objects.isNull(urlDataMap)) {
            return "redirect:/page/404.html";
        }
        Date expireTime = urlDataMap.getExpireTime();
        if (expireTime.before(new Date())) {
            return "redirect:/page/expire.html";
        }

        // 查询到对应的原始链接，先记录访问日志
        urlMapService.updateUrlVisits(request, urlDataMap);
        // 然后302重定向到原地址
        return "redirect:" + urlDataMap.getLurl();
    }

}
