package org.tinycloud.tinyurl.common.enums;


/**
 * 该类为全局系统通用码
 * 业务侧响应码需要业务侧自己定义
 *
 * @author liuxingyu01
 */
public enum CommonCode {
    SUCCESS("0", "操作成功"),

    PARAM_ERROR("400", "参数校验失败"),
    NOT_LOGGED_IN("401", "会话已过期，请重新登录"),
    NOT_EXIST("402", "不存在"),
    FORBIDDEN("403", "权限不足"),
    RESOURCE_NOT_FOUND("404", "资源未找到"),
    RESOURCE_METHOD_NOT_SUPPORT("405", "请求方法不支持"),
    RESOURCE_CONFLICT("409", "资源冲突"),
    UNAUTHRIZATION_OPT("410", "无权限操作，请联系管理员"),
    REQUEST_PARAM_ERROR("412", "参数错误"),
    PRECONDITION_FAILED("428", "要求先决条件"),
    NOT_SUPPORT("409", "不支持的请求"),
    ALREADY_EXECUTING("410", "程序正在执行，请稍后再试"),
    CLIENT_TYPE_IS_NULL("411", "客户端类型不能为空"),
    DOING_IT_TOO_FAST("412", "操作频率过高，请稍后再试！"),
    UNKNOWN_ERROR("500", "系统未知错误"),
    NOT_GATEWAY_FORWARD_REQUEST("501", "非网关转发请求，禁止访问"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String desc;


    private CommonCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private CommonCode() {
    }

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
}
