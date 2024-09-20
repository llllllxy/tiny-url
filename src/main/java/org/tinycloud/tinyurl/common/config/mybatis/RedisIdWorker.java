package org.tinycloud.tinyurl.common.config.mybatis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.tinycloud.tinyurl.common.utils.StrUtils;

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

    @Qualifier("asyncServiceExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public Long nextId(String tableName) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        // 获取今天是今年的第几天
        int dayOfYear = now.getDayOfYear();
        // 天数补零至三位数，如 64 -> 064，为啥要补到三位数，因为最多就只有365天
        String today = year + padWithZero(dayOfYear, 3);

        String keyPrefix = "icr:" + today + ":" + tableName;
        Long current = this.stringRedisTemplate.opsForValue().increment(keyPrefix);
        // 每次都刷新一下缓存的时候，防止服务器时间被前移
        threadPoolTaskExecutor.execute(() -> {
            this.stringRedisTemplate.expire(keyPrefix, expireTime, TimeUnit.SECONDS);
        });
        String number = padWithZero(current, 9);
        // today-7位， number-9位，随机数-3位
        // 相加共19位，如2024064000000090268
        return Long.parseLong(today + number + StrUtils.randomNumber(3));
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
