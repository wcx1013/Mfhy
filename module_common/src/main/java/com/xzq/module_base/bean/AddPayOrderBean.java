package com.xzq.module_base.bean;

import java.util.List;

public class AddPayOrderBean {

    private ParamBean param;

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public static class ParamBean {
        private int amount;
        private String body;
        private String channel;
        private int couponId;
        private String currencyType;
        private List<GoodsListBean> goodsList;
        private String recAccount;
        private String subject;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public String getRecAccount() {
            return recAccount;
        }

        public void setRecAccount(String recAccount) {
            this.recAccount = recAccount;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }


    }
    public static class GoodsListBean {
        private String id;
        private int price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
