package org.tinycloud.tinyurl.common.enums;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-27 14:49
 */
public enum RestfulErrorCode {

    AUTHCODE_NOT_EXIST_OR_EXPIRED("4001", "authCode不存在或已过期，请检查！"),
    AK_NOT_EXIST("4002", "ak不存在，请检查！"),
    AK_IS_EXPIRED("4003", "ak已过期，请检查！"),
    TENANT_IS_DISABLED("4004", "租户已被禁用，请检查！"),
    SIGNATURE_CHECK_FAILED("4005", "签名验签失败，请检查！"),
    IP_IS_NOT_IN_WHITELIST("4006", "源IP不在白名单内，禁止访问！"),


    TOKEN_CAN_NOT_BE_NULL("4100", "token不能为空，禁止访问！"),
    RESTFUL_IS_NOT_LOGIN("4101", "会话已过期，请重新登录！"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private RestfulErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private RestfulErrorCode() {
    }
}
