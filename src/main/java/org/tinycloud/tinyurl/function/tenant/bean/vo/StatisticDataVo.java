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
 * @since 2024-03-2024/3/2 21:45
 */
@Getter
@Setter
public class StatisticDataVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 短链
     */
    private String surl;

    /**
     * 生成时间
     */
    private Date createdAt;

    /**
     * 今日点击量
     */
    private Long accessTodayNumber;

    /**
     * 今日独立IP数
     */
    private Long accessTodayIpNumber;

    /**
     * 昨日点击量
     */
    private Long accessYesterdayNumber;

    /**
     * 昨日独立IP数
     */
    private Long accessYesterdayIpNumber;

    /**
     * 当月点击量
     */
    private Long accessMonthNumber;

    /**
     * 当月独立IP数
     */
    private Long accessMonthIpNumber;

    /**
     * 总点击量
     */
    private Long accessTotalNumber;

    /**
     * 当月独立IP数
     */
    private Long accessTotalIpNumber;
}
