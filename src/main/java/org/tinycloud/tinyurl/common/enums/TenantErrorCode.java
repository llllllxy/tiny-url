package org.tinycloud.tinyurl.common.enums;

public enum TenantErrorCode {
    USERNAME_OR_PASSWORD_MISMATCH("3001", "用户名或密码错误！"),

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
