package org.tinycloud.tinyurl.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.tinycloud.tinyurl.common.enums.CommonCode;
import org.tinycloud.tinyurl.common.exception.BusinessException;
import org.tinycloud.tinyurl.common.model.ApiResult;

import java.util.List;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 13:28
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获404异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult<?> handle404Error(Throwable e) {
        return ApiResult.fail(CommonCode.RESOURCE_NOT_FOUND.getCode(), CommonCode.RESOURCE_NOT_FOUND.getDesc());
    }


    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ApiResult<?> paramBind(BindException e) {
        // 对校验结果统一输出
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField()).append("：").append(error.getDefaultMessage()).append(", ");
        }
        String message = sb.toString();
        if (message != null && message.length() > 2) {
            message = message.substring(0, message.length() - 2);
        }
        return ApiResult.fail(CommonCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 参数校验异常1
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 对校验结果统一输出
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField()).append("：").append(error.getDefaultMessage()).append(", ");
        }
        String message = sb.toString();
        if (message != null && message.length() > 2) {
            message = message.substring(0, message.length() - 2);
        }
        return ApiResult.fail(CommonCode.PARAM_ERROR.getCode(), message);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleBusinessException(BusinessException e) {
        return ApiResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> exception(Throwable throwable) {
        log.error("系统处理异常：", throwable);
        // 参数格式转换异常
        if (throwable instanceof IllegalArgumentException
                || throwable instanceof HttpMessageConversionException
                || throwable instanceof MethodArgumentTypeMismatchException) {
            return ApiResult.fail(CommonCode.PARAM_ERROR.getCode(), CommonCode.PARAM_ERROR.getDesc());
        }
        if (throwable instanceof HttpRequestMethodNotSupportedException) {
            return ApiResult.fail(CommonCode.RESOURCE_METHOD_NOT_SUPPORT.getCode(), CommonCode.RESOURCE_METHOD_NOT_SUPPORT.getDesc());
        }
        return ApiResult.fail(CommonCode.UNKNOWN_ERROR.getCode(), CommonCode.UNKNOWN_ERROR.getDesc());
    }
}
