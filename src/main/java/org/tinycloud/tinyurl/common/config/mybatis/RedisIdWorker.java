package org.tinycloud.tinyurl.common.config.mybatis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <p>
 * 注： js Number 类型最大数值：9007199254740992，之后便会损失精度
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-03-04 17:46
 */
@Component
public class RedisIdWorker {

    /**
     * 缓存的失效时间，缓存30天
     */
    private static final long expireTime = 30 * 24 * 60 * 60;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public Long nextId(String tableName) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        // 获取今天是今年的第几天
        int dayOfYear = now.getDayOfYear();
        // 天数补零至三位数，如 64 -> 064，为啥要补到三位数，因为最多就只有365天
        String today = year + padWithZero(dayOfYear, 3);

        String keyPrefix = "icr:" + today + ":" + tableName;
        Long current = this.stringRedisTemplate.opsForValue().increment(keyPrefix);
        // 如果是第一次的话，刷一下则过期时间为一个月，防止key越攒越多
        if (current == 1L) {
            this.stringRedisTemplate.expire(keyPrefix, expireTime, TimeUnit.SECONDS);
        }
        String number = padWithZero(current, 9);
        // today-7位， number-9位（最大值999999999，一天这么多个ID也够用了，不够的话就再补充长度）
        // 相加共16位，如2024064000000090
        return Long.parseLong(today + number);
    }

    /**
     * 获取指定位数的数字字符串，不足补0
     *
     * @param nextIdValue 数字
     * @param numLen      最终总位数
     * @return 补零后的数字
     */
    private static String padWithZero(long nextIdValue, int numLen) {
        StringBuilder sb = new StringBuilder();
        String nextIdValueString = String.valueOf(nextIdValue);
        int needLen = numLen - nextIdValueString.length();
        for (int i = 0; i < needLen; i++) {
            sb.append("0");
        }
        return sb.append(nextIdValueString).toString();
    }
}
