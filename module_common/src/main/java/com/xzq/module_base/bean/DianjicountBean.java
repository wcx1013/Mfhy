package com.xzq.module_base.bean;

import java.util.List;

public class DianjicountBean {

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
        private List<TagListBean> tagList;
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

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
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

        public static class TagListBean {
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
