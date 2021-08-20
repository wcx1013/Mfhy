package com.xzq.module_base.bean;

public class FeedBackBean {
    public FeedBackBean(){}
    private FeedBackInfo param;

    public FeedBackInfo getParam() {
        return param;
    }

    public void setParam(FeedBackInfo param) {
        this.param = param;
    }

    public static class FeedBackInfo {
public FeedBackInfo(){}
        private String content;
        private String contact;
        private String appId;

        public FeedBackInfo(String content, String contact, String appId) {
            this.content = content;
            this.contact = contact;
            this.appId = appId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
