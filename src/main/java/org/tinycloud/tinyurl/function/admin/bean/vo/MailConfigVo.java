package org.tinycloud.tinyurl.function.admin.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class MailConfigVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键，内码
     */
    private Long id;

    /**
     * smtp服务器地址
     */
    private String smtpAddress;

    /**
     * smtp端口
     */
    private String smtpPort;

    /**
     * 邮箱账号
     */
    private String emailAccount;

    /**
     * 邮箱密码
     */
    private String emailPassword;

    /**
     * 收件邮箱地址(多个用逗号隔开)
     */
    private String receiveEmail;
}
