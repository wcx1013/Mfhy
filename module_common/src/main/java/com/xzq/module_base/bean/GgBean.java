package com.xzq.module_base.bean;

public class GgBean {

    /**
     * param : {"appId":"","channel":"","currentV":""}
     */
public GgBean(){}
    private ParamBean param;

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public static class ParamBean {
        /**
         * appId :
         * channel :
         * currentV :
         */

        private String appId;
        private String channel;
        private String currentV;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getCurrentV() {
            return currentV;
        }

        public void setCurrentV(String currentV) {
            this.currentV = currentV;
        }
    }
}
