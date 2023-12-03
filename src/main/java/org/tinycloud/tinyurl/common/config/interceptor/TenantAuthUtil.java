package org.tinycloud.tinyurl.common.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.common.utils.web.CookieUtils;
import org.tinycloud.tinyurl.common.utils.web.ServletUtils;

/**
 * @author liuxingyu01
 * @date 2022-09-06 10:27
 * @description 租户会话工具类
 **/
public class TenantAuthUtil {


    /**
     *
     * 获取租户会话token
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getToken(HttpServletRequest request) {
        String token = "";
        if (request != null) {
            // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
            token = request.getHeader(GlobalConstant.TENANT_TOKEN_KEY);
            if (StrUtils.isBlank(token)) {
                token = CookieUtils.getCookie(request, GlobalConstant.TENANT_TOKEN_KEY);
            }
        }
        return token;
    }

    /**
     * 获取租户会话token
     *
     * @return String
     */
    public static String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = "";
        if (request != null) {
            // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
            token = request.getHeader(GlobalConstant.TENANT_TOKEN_KEY);
            if (StrUtils.isBlank(token)) {
                token = CookieUtils.getCookie(request, GlobalConstant.TENANT_TOKEN_KEY);
            }
        }
        return token;
    }

}
