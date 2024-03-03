package org.tinycloud.tinyurl.function.admin.bean.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class MailConfigEditDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * smtp服务器地址
     */
    @NotEmpty(message = "smtp服务器地址不能为空！")
    @Pattern(regexp = "^(?!:\\/\\/)(?:[a-zA-Z0-9\\u4e00-\\u9fa5_-]+\\.)*[a-zA-Z0-9\\u4e00-\\u9fa5_-]+\\.[a-zA-Z\\u4e00-\\u9fa5]{2,}$", message = "smtp服务器地址格式不正确！")
    private String smtpAddress;

    /**
     * smtp端口
     */
    @NotNull(message = "smtp端口不能为空！")
    private String smtpPort;

    /**
     * 邮箱账号
     */
    @NotEmpty(message = "发件邮箱地址格式不能为空！")
    @Email(message = "发件邮箱地址格式不正确！")
    private String emailAccount;

    /**
     * 邮箱密码
     */
    @NotEmpty(message = "发件邮箱密码不能为空！")
    private String emailPassword;

    /**
     * 收件邮箱地址(多个用逗号隔开)
     */
    @NotEmpty(message = "收件邮箱地址格式不能为空！")
    @Email(message = "收件邮箱地址格式不正确！")
    private String receiveEmail;
}

