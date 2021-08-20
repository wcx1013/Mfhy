package com.xzq.module_base.api;

import com.google.gson.annotations.SerializedName;
import com.xzq.module_base.mvp.AbsPresenter;

import java.util.List;

/**
 * 请求返回实体
 *
 * @author xzq
 */
public class NetBean<T> {



    private int code;//code
    private String msg;//信息
    private T data;// 返回数据
    @SerializedName(value = "snPageCount", alternate = {"pageCount"})
    private int snPageCount;
    @SerializedName(value = "snTotalCount", alternate = {"count"})
    private int snTotalCount;
    private boolean localHasNextPage;//本地字段

    public boolean isOk() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getTotalCount() {
        return snTotalCount;
    }

    public boolean hasNextPage() {
        return localHasNextPage;
    }

    public void checkHasNextPage(int mPage) {
        boolean hasNextPage;
        final T entity = data;
        if (entity instanceof BaseListBean) {
            hasNextPage = ((BaseListBean) entity).hasNextPage(mPage);
        } else {
            hasNextPage = mPage * AbsPresenter.LIMIT < snTotalCount;
        }
        this.localHasNextPage = hasNextPage;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void copyFrom(NetBean<T> netBean) {
        this.data = netBean.getData();
        this.code = netBean.getCode();
        this.msg = netBean.getMsg();
    }

    /**
     * 判断返回数据是否为空
     *
     * @return 是否为空
     */
    public boolean isDataEmpty() {
        final T entity = data;
        return entity == null
                || (entity instanceof BaseListBean && ((BaseListBean) entity).isEmpty())
                || (entity instanceof List && ((List) entity).isEmpty());
    }

    /**
     * 返回数据是否为列表
     *
     * @return 是否为列表
     */
    public boolean dataIsList() {
        final T entity = data;
        return entity instanceof List || (entity instanceof BaseListBean);
    }
}
