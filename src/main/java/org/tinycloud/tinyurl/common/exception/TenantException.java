package org.tinycloud.tinyurl.common.exception;

import org.tinycloud.tinyurl.common.enums.CommonCode;
import org.tinycloud.tinyurl.common.enums.TenantErrorCode;

/**
 * <p>
 *  统一租户层异常封装
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-24 13:26
 */
public class TenantException extends BusinessException {
    public TenantException(String code, String message) {
        super(code, message);
    }

    public TenantException(String message) {
        this(CommonCode.UNKNOWN_ERROR.getCode(), message);
    }

    public TenantException(TenantErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getDesc());
    }
}
