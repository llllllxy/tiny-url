package org.tinycloud.tinyurl.common.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * <p>
 * 重写Jackson2ObjectMapperBuilderCustomizer，解决实现了以下功能
 * 1、全局转换时间类型的配置，出参Date，LocalDateTime，LocalDate类型的会统一转换为pattern格式的string类型
 * 2、将Long和BigInteger类型转换为字符串类型（主要是为了解决前端js损失精度的问题）
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 15:47:38
 */
@Component
public class CustomJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        // 若POJO对象的属性值为null，序列化时不进行显示（选配）
        // jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);

        // 若POJO对象的属性值为""，序列化时不进行显示（选配）
        // jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_EMPTY);

        // DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES相当于配置，JSON串含有未知字段时，反序列化依旧可以成功
        jacksonObjectMapperBuilder.failOnUnknownProperties(false);

        // 将Long和BigInteger类型转换为字符串类型（主要是为了解决前端js损失精度的问题）
        jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);

        // 针对于Date类型，文本格式化
        jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 全局时区配置
        jacksonObjectMapperBuilder.timeZone("GMT+8");

        // 针对于JDK新时间类(LocalDateTime,LocalDate)。序列化时带有T的问题，自定义格式化字符串
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        jacksonObjectMapperBuilder.modules(javaTimeModule);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
