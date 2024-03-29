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
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 19:04
 */
@Getter
@Setter
@TableName("t_url_access_log")
public class TUrlAccessLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("url_id")
    private Long urlId;

    @TableField("access_time")
    private Date accessTime;

    @TableField("access_ip")
    private String accessIp;

    @TableField("access_address")
    private String accessAddress;

    @TableField("access_user_agent")
    private String accessUserAgent;

    @TableField("created_at")
    private Date createdAt;
}