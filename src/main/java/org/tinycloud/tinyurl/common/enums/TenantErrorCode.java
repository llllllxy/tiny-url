package org.tinycloud.tinyurl.common.enums;

public enum TenantErrorCode {
    TENANT_NOT_LOGIN("3001", "会话已过期，请重新登录！"),

    TENANT_USERNAME_OR_PASSWORD_MISMATCH("3002", "用户名或密码错误！"),
    TENANT_IS_DISABLE("3003", "租户已被禁用，请联系工作人员！"),
    CAPTCHA_IS_MISMATCH("3004", "验证码错误或已过期！"),
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

    private TenantErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private TenantErrorCode() {
    }
}
