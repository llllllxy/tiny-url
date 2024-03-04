package org.tinycloud.tinyurl.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义分布式ID生成器
 * </p >
 *
 * @author liuxingyu01
 * @since 2024-03-04 17:32
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Override
    public Long nextId(Object entity) {
        Class<?> clazz = entity.getClass();
        TableName tableName = (TableName) clazz.getAnnotation(TableName.class);
        // 获取当前操作的表名
        String table = tableName.value();
        return redisIdWorker.nextId(table);
    }
}