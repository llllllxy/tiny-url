package org.tinycloud.tinyurl.function.tenant.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-29 16:27
 */
@Getter
@Setter
public class DashboardQuantityVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long urlQuantity;

    private Long accessQuantity;

    private Long todayAccessQuantity;

    private Long todayIpQuantity;
}
