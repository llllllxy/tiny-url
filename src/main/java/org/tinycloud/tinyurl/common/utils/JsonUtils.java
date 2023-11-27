package org.tinycloud.tinyurl.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Json工具类（基于jackson）
 *
 * @author liuxingyu01
 * @version 2022-03-11-16:47
 **/
public class JsonUtils {
    final static Logger log = LoggerFactory.getLogger(JsonUtils.class);


    /**
     * 对象转JSON字符串
     *
     * @param value 待转换对象
     * @return JSON字符串
     */
    public static String toJsonString(Object value) {
        if (value != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                if (log.isErrorEnabled()) {
                    log.error("JsonUtils -- writeValueAsString -- Exception=", e);
                }
            }
        }
        return null;
    }


    /**
     * JSON字符串转对象
     *
     * @param content   JSON字符串
     * @param valueType 类型
     * @param <T>       对象
     * @return 对象
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        if (content != null && !content.trim().isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(content, valueType);
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("JsonUtils -- readValue -- Exception=", e);
                }
            }
        }

        return null;
    }

    /**
     * JSON字符串转对象
     *
     * @param content      JSON字符串
     * @param valueTypeRef TypeReference
     * @param <T>          对象
     * @return 对象
     */
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        if (content != null && !content.trim().isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(content, valueTypeRef);
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("JsonUtils -- readValue -- Exception=", e);
                }
            }
        }
        return null;
    }


    /**
     * InputStream文件流转对象
     *
     * @param src       输入流
     * @param valueType 类型
     * @param <T>       对象
     * @return 对象
     */
    public static <T> T readValue(InputStream src, Class<T> valueType) {
        if (src != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(src, valueType);
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("JsonUtils -- readValue -- Exception=", e);
                }
            }
        }
        return null;
    }

    /**
     * JSON字符串转List
     *
     * @param content JSON字符串
     * @param clazz   类型
     * @param <T>     对象
     * @return 对象
     */
    public static <T> List<T> readArrayValue(String content, Class<T> clazz) {
        if (content != null && !content.trim().isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(content, objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("JsonUtils -- readArrayValue -- Exception=", e);
                }
            }
        }
        return null;
    }

    /**
     * 类型转换
     * <br/>
     * 示例：String text = "123456";
     * Integer number = JsonUtils.convertValue(text, Integer.class);
     *
     * @param fromValue   原始对象
     * @param toValueType 要转换成的对象类型
     * @param <T>         泛型
     * @return 转换后的对象
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(fromValue, toValueType);
    }

    /**
     * 判断传入的字符串是否是json格式的
     *
     * @param content JSON字符串
     * @return true 是，false 不是
     */
    public static boolean isJson(String content) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            return jsonNode.isObject() || jsonNode.isArray();
        } catch (Exception e) {
            return false;
        }
    }
}
