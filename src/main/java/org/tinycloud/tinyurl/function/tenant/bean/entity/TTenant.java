package org.tinycloud.tinyurl.function.tenant.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     租户表实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-2023/12/3 21:37
 */
@Getter
@Setter
@TableName("t_tenant")
public class TTenant implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("tenant_account")
    private String tenantAccount;

    @TableField("tenant_password")
    private String tenantPassword;

    @TableField("tenant_name")
    private String tenantName;

    @TableField("tenant_phone")
    private String tenantPhone;

    @TableField("tenant_email")
    private String tenantEmail;

    @TableField("status")
    private Integer status;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("created_at")
    private Date createdAt;

    @TableField("expire_time")
    private Date expireTime;

    @TableField("access_key")
    private String accessKey;

    @TableField("access_key_secret")
    private String accessKeySecret;

    @TableField("ip_whitelist")
    private String ipWhitelist;

    @TableField("check_ip_flag")
    private Integer checkIpFlag;
}
