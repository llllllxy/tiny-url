package org.tinycloud.tinyurl.function.tenant.bean.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-2024/2/3 22:11
 */
@Data
public class TenantUrlVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String surl;

    private String lurl;

    private Integer visits;

    private Date expireTime;

    private Integer status;

    private Integer delFlag;

    private Date createdAt;

    private Date updatedAt;

    private String remark;

    private Long tenantId;
}
