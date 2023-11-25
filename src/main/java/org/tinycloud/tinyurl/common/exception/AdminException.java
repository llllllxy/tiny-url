package org.tinycloud.tinyurl.common.exception;

import org.tinycloud.tinyurl.common.enums.AdminErrorCode;
import org.tinycloud.tinyurl.common.enums.CommonCode;

/**
 * <p>
 *  统一管理层异常封装
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-24 13:26
 */
public class AdminException extends BusinessException {
    public AdminException(String code, String message) {
        super(code, message);
    }

    public AdminException(String message) {
        this(CommonCode.UNKNOWN_ERROR.getCode(), message);
    }

    public AdminException(AdminErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getDesc());
    }
}
