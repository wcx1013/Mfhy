package com.xzq.module_base.bean;

import java.util.Map;

public class PayBean {
    private int code;
    private String msg;
    private AddPayOrderInfo result;
    public PayBean(){}
    public PayBean(int code, String msg, AddPayOrderInfo result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AddPayOrderInfo getResult() {
        return result;
    }

    public void setResult(AddPayOrderInfo result) {
        this.result = result;
    }

    public class AddPayOrderInfo{
        private String orderId;
        private String payerType;
        private String signOrder;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPayerType() {
            return payerType;
        }

        public void setPayerType(String payerType) {
            this.payerType = payerType;
        }

        public String getSignOrder() {
            return signOrder;
        }

        public void setSignOrder(String signOrder) {
            this.signOrder = signOrder;
        }
    }

}
