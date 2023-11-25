package org.tinycloud.tinyurl.common.enums;

public enum AdminErrorCode {

    USERNAME_OR_PASSWORD_MISMATCH("2001", "未知错误！"),

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
