package org.tinycloud.tinyurl.function.tenant.bean.vo;

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
 * @since 2024-02-29 09:48
 */
@Getter
@Setter
public class TenantAccessLogVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long tenantId;

    private Long urlId;

    private Date accessTime;

    private String accessIp;

    private String accessAddress;

    private String accessUserAgent;

    private Date createdAt;

    private String surl;
}
