package com.xzq.module_base.api;

import com.google.gson.annotations.SerializedName;
import com.xzq.module_base.mvp.AbsPresenter;

import java.util.List;

/**
 * 列表基类
 *
 * @author xzq
 */

public class BaseListBean<T> {

    @SerializedName(value = "snPageCount", alternate = {"pageCount"})
    private int snPageCount;
    @SerializedName(value = "snTotalCount", alternate = {"count"})
    private int snTotalCount;
    @SerializedName(value = "snData", alternate = {"data", "rows", "datas", "list"})
    private List<T> snData;

    /**
     * 是否还有下一页
     *
     * @param page 当前页码
     * @return 是否还有下一页
     */
    public boolean hasNextPage(final int page) {
        return page * AbsPresenter.LIMIT < snTotalCount;
    }

    public List<T> getList() {
        return snData;
    }

    public int getTotalCount() {
        return snTotalCount;
    }

    public boolean isEmpty() {
        return snData == null || snData.isEmpty();
    }
}
