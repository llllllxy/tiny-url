package org.tinycloud.tinyurl.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * 获取IP地址工具类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 19:11
 */
public class IpGetUtils {
    final static Logger log = LoggerFactory.getLogger(IpGetUtils.class);

    /**
     * 在Nginx等代理之后获取用户真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("getIpAddress exception:", e);
                }
            }
        }
        return StrUtils.substringBefore(ip, ",");
    }



    /**
     * 校验ip是否合法，不合法的返回false
     *
     * @param ip ip地址
     * @return 合法true，不合法false
     */
    public static Boolean checkIp(String ip) {
        //检查ip是否为空
        if (ip == null) {
            return false;
        }
        //检查ip长度，最短为：x.x.x.x（7位）  最长为:xxx.xxx.xxx.xxx（15位）
        if (ip.length() < 7 || ip.length() > 15) {
            return false;
        }
        //对输入字符串的首末字符判断，如果是 "." 则是非法IP
        // charAt() 方法用于返回指定索引处的字符。索引范围为从 0 到 length() - 1
        if (ip.charAt(0) == '.' || ip.charAt(ip.length() - 1) == '.') {
            return false;
        }
        //按 "." 分割字符串，并判断分割出来的个数，如果不是4个，则是非法IP
        String[] arr = ip.split("\\.");
        if (arr.length != 4) {
            return false;
        }
        //对分割出来的每个字符串进行单独判断
        for (String s : arr) {
            //如果每个字符串不是一位字符，且以 '0' 开头，则是非法的ip,如：01.123.23.124 ,
            if (s.length() > 1 && s.charAt(0) == '0') {
                return false;
            }
            // 对每个字符串的每个字符进行逐一判断，如果不是数字0-9，则是非法的ip 如： 64.12.22.-1  针对 6、4、1、2、2、2、-1 逐个数字判断
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) < '0' || s.charAt(j) > '9') {
                    return false;
                }
            }
        }
        //对拆分的每一个字符串进行转换成数字，并判断是否在 0 ~ 255
        for (int i = 0; i < arr.length; i++) {
            int temp = Integer.parseInt(arr[i]);
            if (i == 0) {
                if (temp < 1 || temp > 255) {
                    return false;
                }
            } else {
                if (temp < 0 || temp > 255) {
                    return false;
                }
            }
        }
        return true;
    }
}
