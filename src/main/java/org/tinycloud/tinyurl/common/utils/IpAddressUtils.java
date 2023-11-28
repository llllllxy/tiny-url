package org.tinycloud.tinyurl.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 根据ip获取地理位置信息
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-27 14:14
 **/
public class IpAddressUtils {
    final static Logger logger = LoggerFactory.getLogger(IpAddressUtils.class);

    private static RestTemplate restTemplate;

    private static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            RestTemplate bean = SpringContextUtils.getBean(RestTemplate.class);
            if (bean == null) {
                logger.error("DictConvertUtilsService bean is null");
            }
            restTemplate = bean;
            return restTemplate;
        }
        return restTemplate;
    }


    /**
     * 根据IP地址获取地理位置
     *
     * @param ip ip地址
     * @return 地理位置信息
     */
    public static String getAddressByIP(String ip) {
        if (!StringUtils.hasText(ip)) {
            return "ip为空，无法获取位置";
        }
        if ("127.0.0.1".equals(ip)) {
            return "局域网，无法获取位置";
        }
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        String result = getRestTemplate().getForObject(url, String.class);
        Map resultMap = JsonUtils.readValue(result, Map.class);
        String status = Optional.ofNullable(resultMap.get("status")).orElse("").toString();
        if (StringUtils.hasText(status) && status.equals("0")) {
            List resultList = (List) resultMap.get("data");
            if (!CollectionUtils.isEmpty(resultList)) {
                Map dataMap = (Map) resultList.get(0);
                return (String) dataMap.get("location");
            } else {
                return "获取位置失败";
            }
        } else {
            return "获取位置失败";
        }
    }

    public static void main(String[] args) {
        System.out.println(getAddressByIP("42.192.52.63"));
    }
}
