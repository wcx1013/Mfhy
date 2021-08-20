package com.xzq.module_base.bean;

import java.io.Serializable;


public class TuijianBean implements Serializable {

    /**
     * order : desc
     * pageNum : 0
     * pageSize : 0
     * param : {"createEndTime":"","createStartTime":"","resType":""}
     * sort : add_time
     */

  //  private String order;
    private int pageNum;
    private int pageSize;
    private ParamDTO param;
   // private String sort;

public TuijianBean(){}
    public TuijianBean(int pageNum, int pageSize, ParamDTO param) {
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
         * createEndTime :
         * createStartTime :
         * resType :
         */

       // private String createEndTime;
      //  private String createStartTime;

        private String resType;
        public ParamDTO(){}
        public ParamDTO(String resType) {
            this.resType = resType;
        }

        public String getResType() {
            return resType;
        }

        public void setResType(String resType) {
            this.resType = resType;
        }
    }
}
