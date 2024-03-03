package org.tinycloud.tinyurl.function.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.function.admin.bean.dto.MailConfigEditDto;
import org.tinycloud.tinyurl.function.admin.bean.vo.MailConfigVo;
import org.tinycloud.tinyurl.function.admin.service.MailConfigService;


@RestController
@RequestMapping("/admin/mailconfig")
public class MailConfigController {

    @Autowired
    private MailConfigService mailConfigService;

    @GetMapping("/detail")
    public ApiResult<MailConfigVo> detail() {
        return ApiResult.success(mailConfigService.detail(), "获取成功");
    }

    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody MailConfigEditDto dto) {
        return ApiResult.success(mailConfigService.edit(dto), "修改成功");
    }
}
