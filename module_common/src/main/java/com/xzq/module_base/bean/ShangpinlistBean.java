package com.xzq.module_base.bean;

public class ShangpinlistBean {

    private String order;
    private int pageNum;
    private int pageSize;
    private ParamBean param;
    private String sort;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public static class ParamBean {
        private String category;
        private String createEndTime;
        private String createStartTime;
        private String goodsStatus;
        private int originalPrice;
        private int price;
        private String productCode;
        private String refId;
        private String title;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreateEndTime() {
            return createEndTime;
        }

        public void setCreateEndTime(String createEndTime) {
            this.createEndTime = createEndTime;
        }

        public String getCreateStartTime() {
            return createStartTime;
        }

        public void setCreateStartTime(String createStartTime) {
            this.createStartTime = createStartTime;
        }

        public String getGoodsStatus() {
            return goodsStatus;
        }

        public void setGoodsStatus(String goodsStatus) {
            this.goodsStatus = goodsStatus;
        }

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
