package com.wd.winddots.desktop.list.check.bean;

import java.util.List;

/**
 * FileName: CheckDetailBean
 * Author: 郑
 * Date: 2021/1/15 11:49 AM
 * Description:
 */
public class CheckDetailBean {

    private String id;
    private String sno;
    private String lengthBefore;
    private String lengthAfter;
    private String lengthUnit;
    private String weightAfter;
    private String weightBefore;
    private String weightUnit;
    private String createTime;
    private String modifyTime;
    private List<CheckDetailItem>recordProblemList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getLengthBefore() {
        return lengthBefore;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public String getLengthAfter() {
        return lengthAfter;
    }

    public void setLengthAfter(String lengthAfter) {
        this.lengthAfter = lengthAfter;
    }

    public String getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public String getWeightAfter() {
        return weightAfter;
    }

    public void setWeightAfter(String weightAfter) {
        this.weightAfter = weightAfter;
    }

    public String getWeightBefore() {
        return weightBefore;
    }

    public void setWeightBefore(String weightBefore) {
        this.weightBefore = weightBefore;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<CheckDetailItem> getRecordProblemList() {
        return recordProblemList;
    }

    public void setRecordProblemList(List<CheckDetailItem> recordProblemList) {
        this.recordProblemList = recordProblemList;
    }

    public static class CheckDetailItem{
        private String id;
        private String recordId;
        private String tag;
        private String tagATimes;
        private String tagBTimes;
        private String tagCTimes;
        private String tagDTimes;
        private String remark;
        private String image;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTagATimes() {
            return tagATimes;
        }

        public void setTagATimes(String tagATimes) {
            this.tagATimes = tagATimes;
        }

        public String getTagBTimes() {
            return tagBTimes;
        }

        public void setTagBTimes(String tagBTimes) {
            this.tagBTimes = tagBTimes;
        }

        public String getTagCTimes() {
            return tagCTimes;
        }

        public void setTagCTimes(String tagCTimes) {
            this.tagCTimes = tagCTimes;
        }

        public String getTagDTimes() {
            return tagDTimes;
        }

        public void setTagDTimes(String tagDTimes) {
            this.tagDTimes = tagDTimes;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
