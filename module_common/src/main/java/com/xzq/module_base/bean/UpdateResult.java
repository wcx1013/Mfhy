package com.xzq.module_base.bean;

import java.io.Serializable;
import java.util.List;


public class UpdateResult implements Serializable {
    public UpdateResult(){}
    /**
     * code :
     * msg :
     * result : {"appConfig":"","appId":"","detail":"","id":"","ifPay":0,"name":"","platform":"","recAccountList":[{"id":"","ifSandbox":0,"name":"","payerType":""}],"size":0,"startAdFlag":"","status":"","updateContent":"","updateV":{"appId":"","auditRemark":"","channel":"","id":"","onlineTime":"","platform":"","remark":"","size":0,"status":"","updateContent":"","updateType":"","url":"","version":""},"url":"","userAgreement":"","vRemark":"","vType":"","version":"","versionId":""}
     */

    private String code;
    private String msg;
    private ResultDTO result;

    public UpdateResult(String code, String msg, ResultDTO result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

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

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO implements Serializable {
        /**
         * appConfig :
         * appId :
         * detail :
         * id :
         * ifPay : 0
         * name :
         * platform :
         * recAccountList : [{"id":"","ifSandbox":0,"name":"","payerType":""}]
         * size : 0
         * startAdFlag :
         * status :
         * updateContent :
         * updateV : {"appId":"","auditRemark":"","channel":"","id":"","onlineTime":"","platform":"","remark":"","size":0,"status":"","updateContent":"","updateType":"","url":"","version":""}
         * url :
         * userAgreement :
         * vRemark :
         * vType :
         * version :
         * versionId :
         */

        private String appConfig;
        private String appId;
        private String detail;
        private String id;
        private int ifPay;
        private String name;
        private String platform;
        private int size;
        private String startAdFlag;
        private String status;
        private String updateContent;
        private UpdateVDTO updateV;
        private String url;
        private String userAgreement;
        private String vRemark;
        private String vType;
        private String version;
        private String versionId;
        private List<RecAccountListDTO> recAccountList;

        public ResultDTO(String appConfig, String appId, String detail, String id, int ifPay, String name, String platform, int size, String startAdFlag, String status, String updateContent, UpdateVDTO updateV, String url, String userAgreement, String vRemark, String vType, String version, String versionId, List<RecAccountListDTO> recAccountList) {
            this.appConfig = appConfig;
            this.appId = appId;
            this.detail = detail;
            this.id = id;
            this.ifPay = ifPay;
            this.name = name;
            this.platform = platform;
            this.size = size;
            this.startAdFlag = startAdFlag;
            this.status = status;
            this.updateContent = updateContent;
            this.updateV = updateV;
            this.url = url;
            this.userAgreement = userAgreement;
            this.vRemark = vRemark;
            this.vType = vType;
            this.version = version;
            this.versionId = versionId;
            this.recAccountList = recAccountList;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
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

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public UpdateVDTO getUpdateV() {
            return updateV;
        }

        public void setUpdateV(UpdateVDTO updateV) {
            this.updateV = updateV;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserAgreement() {
            return userAgreement;
        }

        public void setUserAgreement(String userAgreement) {
            this.userAgreement = userAgreement;
        }

        public String getvRemark() {
            return vRemark;
        }

        public void setvRemark(String vRemark) {
            this.vRemark = vRemark;
        }

        public String getvType() {
            return vType;
        }

        public void setvType(String vType) {
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

        public List<RecAccountListDTO> getRecAccountList() {
            return recAccountList;
        }

        public void setRecAccountList(List<RecAccountListDTO> recAccountList) {
            this.recAccountList = recAccountList;
        }

        public static class UpdateVDTO implements Serializable {
            /**
             * appId :
             * auditRemark :
             * channel :
             * id :
             * onlineTime :
             * platform :
             * remark :
             * size : 0
             * status :
             * updateContent :
             * updateType :
             * url :
             * version :
             */

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

            public UpdateVDTO(String appId, String auditRemark, String channel, String id, String onlineTime, String platform, String remark, int size, String status, String updateContent, String updateType, String url, String version) {
                this.appId = appId;
                this.auditRemark = auditRemark;
                this.channel = channel;
                this.id = id;
                this.onlineTime = onlineTime;
                this.platform = platform;
                this.remark = remark;
                this.size = size;
                this.status = status;
                this.updateContent = updateContent;
                this.updateType = updateType;
                this.url = url;
                this.version = version;
            }

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


        public static class RecAccountListDTO implements Serializable {
            /**
             * id :
             * ifSandbox : 0
             * name :
             * payerType :
             */

            private String id;
            private int ifSandbox;
            private String name;
            private String payerType;

            public RecAccountListDTO(String id, int ifSandbox, String name, String payerType) {
                this.id = id;
                this.ifSandbox = ifSandbox;
                this.name = name;
                this.payerType = payerType;
            }

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
