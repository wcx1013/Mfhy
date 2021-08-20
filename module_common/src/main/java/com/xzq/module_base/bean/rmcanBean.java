package com.xzq.module_base.bean;

import java.io.Serializable;


public class rmcanBean implements Serializable {
    /**
     * pageNum : 0
     * pageSize : 0
     * param : {"contentType":"","createEndTime":"","createStartTime":"","level":0,"name":"","parentId":"","tagId":"","type":""}
     */

    private int pageNum;
    private int pageSize;

    private ParamDTO param;
public rmcanBean(){}
    public rmcanBean(int pageNum, int pageSize, ParamDTO param) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.param = param;
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

    public ParamDTO getParam() {
        return param;
    }

    public void setParam(ParamDTO param) {
        this.param = param;
    }

    public static class ParamDTO implements Serializable {
        /**
         * contentType :
         * createEndTime :
         * createStartTime :
         * level : 0
         * name :
         * parentId :
         * tagId :
         * type :
         */

        private String contentType;
        private String createEndTime;
        private String createStartTime;
      //  private int level;
        private String name;
        private String parentId;
        private String tagId;
        private String type;

        public ParamDTO(){}
        public ParamDTO(String contentType, String createEndTime, String createStartTime, String name, String parentId, String tagId, String type) {
            this.contentType = contentType;
            this.createEndTime = createEndTime;
            this.createStartTime = createStartTime;
         //   this.level = level;
            this.name = name;
            this.parentId = parentId;
            this.tagId = tagId;
            this.type = type;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
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

//        public int getLevel() {
//            return level;
//        }
//
//        public void setLevel(int level) {
//            this.level = level;
//        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

//    private String Authorization;
//    private int pageNum;
//    private int pageSize;
//    private int type;
//public rmcanBean(){}
//    public rmcanBean(String authorization, int pageNum, int pageSize, int type) {
//        Authorization = authorization;
//        this.pageNum = pageNum;
//        this.pageSize = pageSize;
//        this.type = type;
//    }
//
//    public String getAuthorization() {
//        return Authorization;
//    }
//
//    public void setAuthorization(String authorization) {
//        Authorization = authorization;
//    }
//
//    public int getPageNum() {
//        return pageNum;
//    }
//
//    public void setPageNum(int pageNum) {
//        this.pageNum = pageNum;
//    }
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

}
