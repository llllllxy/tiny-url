package org.tinycloud.tinyurl.common.model;

import java.io.Serial;
import java.io.Serializable;


/**
 * <p>
 * 分页基类，分页查询时,dto可继承此类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-03-07 15:47:38
 */
public class BasePageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;

    /** 第几页 */
    private Integer pageNo = 1;

    /** 第每页多少行 */
    private Integer pageSize = 10;

    /** 排序字段，根据XXX ordey by */
    private String sortFiled;

    /** 降序还是升序，asc or desc */
    private String sortType;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
