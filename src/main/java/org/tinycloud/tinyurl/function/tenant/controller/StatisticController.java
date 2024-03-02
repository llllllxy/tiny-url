package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.model.PageModel;
import org.tinycloud.tinyurl.function.tenant.bean.dto.StatisticQueryDto;
import org.tinycloud.tinyurl.function.tenant.bean.vo.StatisticDataVo;
import org.tinycloud.tinyurl.function.tenant.service.StatisticService;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-2024/3/2 21:43
 */
@Slf4j
@RestController
@RequestMapping("/tenant/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/query")
    public ApiResult<PageModel<StatisticDataVo>> query(@Validated @RequestBody StatisticQueryDto dto) {
        return ApiResult.success(statisticService.query(dto), "查询成功！");
    }
}
