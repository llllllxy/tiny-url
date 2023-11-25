package org.tinycloud.tinyurl.common.exception;


import org.tinycloud.tinyurl.common.enums.CommonCode;

/**
 * <p>
 * 统一业务异常封装
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 13:26
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    private Object errT;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrT() {
        return errT;
    }

    public void setErrT(Object errT) {
        this.errT = errT;
    }

    private BusinessException(String code, String message, Object errT) {
        super(message);
        this.code = code;
        this.message = message;
        this.errT = errT;
    }

    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    public BusinessException(CommonCode code) {
        this(code.getCode(), code.getDesc());
    }

    public BusinessException(CommonCode code, Object errT) {
        this(code.getCode(), code.getDesc(), errT);
    }
}
