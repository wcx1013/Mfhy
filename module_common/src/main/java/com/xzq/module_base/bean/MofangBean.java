package com.xzq.module_base.bean;

import java.io.Serializable;
import java.util.List;


public class MofangBean implements Serializable {

    /**
     * code :
     * list : [{"baseShareTimes":0,"baseThumbTimes":0,"content":"","contentType":"","coverImgUrl":"","id":"","leafNodeCount":0,"level":0,"name":"","orderNo":0,"parentId":"","shareTime":"","shareTimes":0,"shared":0,"stDesc":"","star":0,"starTime":"","starTimes":0,"tagList":[{"code":"","detail":"","id":"","isLeaf":0,"level":0,"name":"","orderNo":0,"parentCode":"","parentId":"","type":""}],"tags":"","tagsId":"","tagsName":"","thumbTimes":0,"thumbUp":0,"thumbUpTime":"","time":0,"type":"","viewTimes":0,"watchTime":"","watched":0}]
     * msg :
     * total : 0
     */

    private String code;
    private String msg;
    private int total;
    private List<ListDTO> list;

    public MofangBean(String code, String msg, int total, List<ListDTO> list) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.list = list;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO implements Serializable {
        private int baseShareTimes;
        private int baseThumbTimes;
        private String content;
        private String contentType;
        private String coverImgUrl;
        private String id;
        private int leafNodeCount;
        private int level;
        private String name;
        private int orderNo;
        private String parentId;
        private String shareTime;
        private int shareTimes;
        private int shared;
        private String stDesc;
        private int star;
        private String starTime;
        private int starTimes;
        private String tags;
        private String tagsId;
        private String tagsName;
        private int thumbTimes;
        private int thumbUp;
        private String thumbUpTime;
        private int time;
        private String type;
        private int viewTimes;
        private String watchTime;
        private int watched;
        private List<TagListDTO> tagList;

        public ListDTO(int baseShareTimes, int baseThumbTimes, String content, String contentType, String coverImgUrl, String id, int leafNodeCount, int level, String name, int orderNo, String parentId, String shareTime, int shareTimes, int shared, String stDesc, int star, String starTime, int starTimes, String tags, String tagsId, String tagsName, int thumbTimes, int thumbUp, String thumbUpTime, int time, String type, int viewTimes, String watchTime, int watched, List<TagListDTO> tagList) {
            this.baseShareTimes = baseShareTimes;
            this.baseThumbTimes = baseThumbTimes;
            this.content = content;
            this.contentType = contentType;
            this.coverImgUrl = coverImgUrl;
            this.id = id;
            this.leafNodeCount = leafNodeCount;
            this.level = level;
            this.name = name;
            this.orderNo = orderNo;
            this.parentId = parentId;
            this.shareTime = shareTime;
            this.shareTimes = shareTimes;
            this.shared = shared;
            this.stDesc = stDesc;
            this.star = star;
            this.starTime = starTime;
            this.starTimes = starTimes;
            this.tags = tags;
            this.tagsId = tagsId;
            this.tagsName = tagsName;
            this.thumbTimes = thumbTimes;
            this.thumbUp = thumbUp;
            this.thumbUpTime = thumbUpTime;
            this.time = time;
            this.type = type;
            this.viewTimes = viewTimes;
            this.watchTime = watchTime;
            this.watched = watched;
            this.tagList = tagList;
        }

        public int getBaseShareTimes() {
            return baseShareTimes;
        }

        public void setBaseShareTimes(int baseShareTimes) {
            this.baseShareTimes = baseShareTimes;
        }

        public int getBaseThumbTimes() {
            return baseThumbTimes;
        }

        public void setBaseThumbTimes(int baseThumbTimes) {
            this.baseThumbTimes = baseThumbTimes;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLeafNodeCount() {
            return leafNodeCount;
        }

        public void setLeafNodeCount(int leafNodeCount) {
            this.leafNodeCount = leafNodeCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getShareTime() {
            return shareTime;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public int getShareTimes() {
            return shareTimes;
        }

        public void setShareTimes(int shareTimes) {
            this.shareTimes = shareTimes;
        }

        public int getShared() {
            return shared;
        }

        public void setShared(int shared) {
            this.shared = shared;
        }

        public String getStDesc() {
            return stDesc;
        }

        public void setStDesc(String stDesc) {
            this.stDesc = stDesc;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getStarTime() {
            return starTime;
        }

        public void setStarTime(String starTime) {
            this.starTime = starTime;
        }

        public int getStarTimes() {
            return starTimes;
        }

        public void setStarTimes(int starTimes) {
            this.starTimes = starTimes;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTagsId() {
            return tagsId;
        }

        public void setTagsId(String tagsId) {
            this.tagsId = tagsId;
        }

        public String getTagsName() {
            return tagsName;
        }

        public void setTagsName(String tagsName) {
            this.tagsName = tagsName;
        }

        public int getThumbTimes() {
            return thumbTimes;
        }

        public void setThumbTimes(int thumbTimes) {
            this.thumbTimes = thumbTimes;
        }

        public int getThumbUp() {
            return thumbUp;
        }

        public void setThumbUp(int thumbUp) {
            this.thumbUp = thumbUp;
        }

        public String getThumbUpTime() {
            return thumbUpTime;
        }

        public void setThumbUpTime(String thumbUpTime) {
            this.thumbUpTime = thumbUpTime;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getViewTimes() {
            return viewTimes;
        }

        public void setViewTimes(int viewTimes) {
            this.viewTimes = viewTimes;
        }

        public String getWatchTime() {
            return watchTime;
        }

        public void setWatchTime(String watchTime) {
            this.watchTime = watchTime;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public List<TagListDTO> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListDTO> tagList) {
            this.tagList = tagList;
        }

        public static class TagListDTO implements Serializable {
            private String code;
            private String detail;
            private String id;
            private int isLeaf;
            private int level;
            private String name;
            private int orderNo;
            private String parentCode;
            private String parentId;
            private String type;

            public TagListDTO(String code, String detail, String id, int isLeaf, int level, String name, int orderNo, String parentCode, String parentId, String type) {
                this.code = code;
                this.detail = detail;
                this.id = id;
                this.isLeaf = isLeaf;
                this.level = level;
                this.name = name;
                this.orderNo = orderNo;
                this.parentCode = parentCode;
                this.parentId = parentId;
                this.type = type;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public int getIsLeaf() {
                return isLeaf;
            }

            public void setIsLeaf(int isLeaf) {
                this.isLeaf = isLeaf;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(int orderNo) {
                this.orderNo = orderNo;
            }

            public String getParentCode() {
                return parentCode;
            }

            public void setParentCode(String parentCode) {
                this.parentCode = parentCode;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }


    }
}
