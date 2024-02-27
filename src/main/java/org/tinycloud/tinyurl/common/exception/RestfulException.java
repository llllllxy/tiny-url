package org.tinycloud.tinyurl.common.exception;

import org.tinycloud.tinyurl.common.enums.CommonCode;
import org.tinycloud.tinyurl.common.enums.RestfulErrorCode;

/**
 * <p>
 *     统一restful层异常封装
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-27 14:49
 */
public class RestfulException extends BusinessException{
    public RestfulException(String code, String message) {
        super(code, message);
    }

    public RestfulException(String message) {
        this(CommonCode.UNKNOWN_ERROR.getCode(), message);
    }

    public RestfulException(RestfulErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getDesc());
    }
}
