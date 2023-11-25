package org.tinycloud.tinyurl.common.exception;

import org.tinycloud.tinyurl.common.enums.CommonCode;
import org.tinycloud.tinyurl.common.enums.CoreErrorCode;

/**
 * <p>
 * 统一核心层异常封装
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-24 13:26
 */
public class CoreException extends BusinessException {
    public CoreException(String code, String message) {
        super(code, message);
    }

    public CoreException(String message) {
        this(CommonCode.UNKNOWN_ERROR.getCode(), message);
    }

    public CoreException(CoreErrorCode coreErrorCode) {
        this(coreErrorCode.getCode(), coreErrorCode.getDesc());
    }
}
