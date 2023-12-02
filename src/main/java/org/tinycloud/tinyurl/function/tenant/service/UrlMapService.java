package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.utils.*;
import org.tinycloud.tinyurl.common.utils.web.UserAgentUtils;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlAccessLog;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TUrlMap;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlAccessLogMapper;
import org.tinycloud.tinyurl.function.tenant.mapper.UrlMapMapper;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-29 10:38
 */
@Slf4j
@Service
public class UrlMapService {

    @Autowired
    private UrlMapMapper urlMapMapper;

    @Autowired
    private UrlAccessLogMapper urlAccessLogMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThreadPoolTaskExecutor asyncServiceExecutor;


    // 自定义长链接防重复字符串
    private static final String DUPLICATE = "*";

    // 最近使用的短链接缓存过期时间(分钟)
    private static final long TIMEOUT = 10;

    // 布隆过滤器预计要插入多少数据
    private static final int SIZE = 1000000;

    // 布隆过滤器期望的误判率
    private static final double FPP = 0.01;

    // guava提供的jvm级别的布隆过滤器
    private static final BloomFilter<String> BLOOM_FILTER = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), SIZE, FPP);


    public TUrlMap getUrlMapBySurl(String surl) {
        String jsonString = stringRedisTemplate.opsForValue().get(GlobalConstant.URL_CACHE_REDIS_KEY + surl);
        if (StrUtils.isNotEmpty(jsonString)) {
            return JsonUtils.readValue(jsonString, TUrlMap.class);
        } else {
            TUrlMap urlMap = this.urlMapMapper.selectOne(Wrappers.<TUrlMap>lambdaQuery()
                    .eq(TUrlMap::getDelFlag, GlobalConstant.NOT_DELETED)
                    .eq(TUrlMap::getSurl, surl));
            if (Objects.nonNull(urlMap)) {
                stringRedisTemplate.opsForValue().set(GlobalConstant.URL_CACHE_REDIS_KEY + surl, JsonUtils.toJsonString(urlMap), TIMEOUT, TimeUnit.MINUTES);
                return urlMap;
            } else {
                return null;
            }
        }
    }


    /**
     * 生成并保存短链（一开始是递归，后来改成while循环了）
     *
     * @param tUrlMap 原始链接信息
     * @return 生成的短链信息
     */
    public TUrlMap generateAndSave(TUrlMap tUrlMap) {
        if (log.isInfoEnabled()) {
            log.info("UrlMapService -- generateAndSave -- tUrlMap = {}", tUrlMap);
        }
        String tempURL = tUrlMap.getLurl();
        String shortURL = MurmurHashUtils.hashToBase62(tempURL);

        // 保留长度为1的短链接（不允许长度为1，长度为1的强行再hash一次）
        while (shortURL.length() == 1) {
            tempURL += DUPLICATE;
            shortURL = MurmurHashUtils.hashToBase62(tempURL);
        }

        // 在布隆过滤器中查找是否存在，如果已经存在了，则再重新HASH一次（还是为了提高性能，减少插入时撞主键的概率，减少数据库的压力嘛）
        boolean ifContain = true;
        while (ifContain) {
            // 可能存在，重新HASH
            if (BLOOM_FILTER.mightContain(shortURL)) {
                tempURL += DUPLICATE;
                shortURL = MurmurHashUtils.hashToBase62(tempURL);
            } else {
                // 不存在则跳出循环，继续往下走
                ifContain = false;
            }
        }

        // 直接存入数据库，如果触发异常的话，说明违反了 surl 的唯一性索引，那说明数据库中已经存在此shortURL了
        // 则拼接上DUPLICATE，继续hash，直到不再冲突
        boolean ifContinue = true;
        while (ifContinue) {
            try {
                tUrlMap.setSurl(shortURL);
                // 直到数据库中不存在此shortURL，那则可以进行数据库插入了
                this.urlMapMapper.insert(tUrlMap);
                // 放入到布隆过滤器中去
                BLOOM_FILTER.put(shortURL);
                // 同时添加redis缓存
                ifContinue = false;
            } catch (Exception e) {
                // 数据库已经存在此短链接，则可能是布隆过滤器误判，则在长链接后加上指定字符串，继续重新hash
                if (e instanceof DuplicateKeyException) {
                    tempURL += DUPLICATE;
                    shortURL = MurmurHashUtils.hashToBase62(tempURL);
                } else {
                    log.error("UrlMapService -- generateAndSave -- Exception: ", e);
                    throw e;
                }
            }
        }
        return tUrlMap;
    }


    /**
     * 更新访问次数，插入访问日志
     *
     * @param surl 短链
     */
    public void updateUrlVisits(HttpServletRequest request, TUrlMap entity) {
        // 获取ip这一行不能放在异步执行
        final String accessIp = IpGetUtils.getIpAddr(request);
        final String accessUserAgent = JsonUtils.toJsonString(UserAgentUtils.getUserAgent(request));
        asyncServiceExecutor.execute(() -> {
            // 先更新访问次数
            this.urlMapMapper.updateUrlVisits(entity.getId());

            // 再插入访问日志
            TUrlAccessLog urlAccessLog = new TUrlAccessLog();
            urlAccessLog.setTenantId(entity.getTenantId());
            urlAccessLog.setUrlId(entity.getId());
            urlAccessLog.setAccessIp(accessIp);
            String accessAddress = IpAddressUtils.getAddressByIP(accessIp);
            urlAccessLog.setAccessAddress(accessAddress);
            urlAccessLog.setAccessUserAgent(accessUserAgent);
            urlAccessLog.setAccessTime(new Date());
            this.urlAccessLogMapper.insert(urlAccessLog);
        });
    }
}
