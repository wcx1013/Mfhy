package com.xzq.module_base.bean;

import java.util.List;

public class HuoappBean {

    private String code;
    private String msg;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String appConfig;
        private String appId;
        private String detail;
        private String huawei;
        private String id;
        private int ifPay;
        private String millet;
        private String name;
        private String oppo;
        private String platform;
        private List<RecAccountListBean> recAccountList;
        private int size;
        private String startAdFlag;
        private String status;
        private String tencent;
        private String updateContent;
        private UpdateVBean updateV;
        private String url;
        private String vRemark;
        private String vType;
        private String version;
        private String versionId;
        private String vivo;

        public String getAppConfig() {
            return appConfig;
        }

        public void setAppConfig(String appConfig) {
            this.appConfig = appConfig;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getHuawei() {
            return huawei;
        }

        public void setHuawei(String huawei) {
            this.huawei = huawei;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIfPay() {
            return ifPay;
        }

        public void setIfPay(int ifPay) {
            this.ifPay = ifPay;
        }

        public String getMillet() {
            return millet;
        }

        public void setMillet(String millet) {
            this.millet = millet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOppo() {
            return oppo;
        }

        public void setOppo(String oppo) {
            this.oppo = oppo;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public List<RecAccountListBean> getRecAccountList() {
            return recAccountList;
        }

        public void setRecAccountList(List<RecAccountListBean> recAccountList) {
            this.recAccountList = recAccountList;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getStartAdFlag() {
            return startAdFlag;
        }

        public void setStartAdFlag(String startAdFlag) {
            this.startAdFlag = startAdFlag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTencent() {
            return tencent;
        }

        public void setTencent(String tencent) {
            this.tencent = tencent;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public UpdateVBean getUpdateV() {
            return updateV;
        }

        public void setUpdateV(UpdateVBean updateV) {
            this.updateV = updateV;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVRemark() {
            return vRemark;
        }

        public void setVRemark(String vRemark) {
            this.vRemark = vRemark;
        }

        public String getVType() {
            return vType;
        }

        public void setVType(String vType) {
            this.vType = vType;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getVivo() {
            return vivo;
        }

        public void setVivo(String vivo) {
            this.vivo = vivo;
        }

        public static class UpdateVBean {
            private String appId;
            private String auditRemark;
            private String channel;
            private String id;
            private String onlineTime;
            private String platform;
            private String remark;
            private int size;
            private String status;
            private String updateContent;
            private String updateType;
            private String url;
            private String version;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getAuditRemark() {
                return auditRemark;
            }

            public void setAuditRemark(String auditRemark) {
                this.auditRemark = auditRemark;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOnlineTime() {
                return onlineTime;
            }

            public void setOnlineTime(String onlineTime) {
                this.onlineTime = onlineTime;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUpdateContent() {
                return updateContent;
            }

            public void setUpdateContent(String updateContent) {
                this.updateContent = updateContent;
            }

            public String getUpdateType() {
                return updateType;
            }

            public void setUpdateType(String updateType) {
                this.updateType = updateType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }
        }

        public static class RecAccountListBean {
            private String id;
            private int ifSandbox;
            private String name;
            private String payerType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIfSandbox() {
                return ifSandbox;
            }

            public void setIfSandbox(int ifSandbox) {
                this.ifSandbox = ifSandbox;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPayerType() {
                return payerType;
            }

            public void setPayerType(String payerType) {
                this.payerType = payerType;
            }
        }
    }
}
