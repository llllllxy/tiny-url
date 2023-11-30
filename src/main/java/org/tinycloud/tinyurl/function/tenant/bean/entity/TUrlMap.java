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
 * 租户短链信息映射表
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-28 14:13
 */
@Getter
@Setter
@TableName("t_url_map")
public class TUrlMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("surl")
    private String surl;

    @TableField("lurl")
    private String lurl;

    @TableField("visits")
    private Integer visits;

    @TableField("expire_time")
    private Date expireTime;

    @TableField("status")
    private Integer status;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;

    @TableField("remark")
    private String remark;

    @TableField("tenant_id")
    private Long tenantId;
}
