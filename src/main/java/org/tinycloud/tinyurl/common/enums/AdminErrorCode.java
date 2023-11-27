package org.tinycloud.tinyurl.common.enums;

public enum AdminErrorCode {
    ADMIN_NOT_LOGIN("2001", "会话已过期，请重新登录！"),

    USERNAME_OR_PASSWORD_MISMATCH("2002", "用户名或密码错误！"),

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

    private AdminErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private AdminErrorCode() {
    }
}
