package com.wd.winddots.entity;

import java.util.List;

/**
 * FileName: FabricCheckProblemBean
 * Author: 郑
 * Date: 2021/3/2 2:40 PM
 * Description:
 */
public class FabricCheckProblemBean {

    private List<FabricCheckProblemSelect> fabricCheckProblemConfigList;

    private List<FabricCheckTaskRecordPosition> fabricCheckRecordProblemPositionList;

    private FabricCheckWidth fabricCheckRecord;


    public List<FabricCheckProblemSelect> getFabricCheckProblemConfigList() {
        return fabricCheckProblemConfigList;
    }

    public void setFabricCheckProblemConfigList(List<FabricCheckProblemSelect> fabricCheckProblemConfigList) {
        this.fabricCheckProblemConfigList = fabricCheckProblemConfigList;
    }

    public List<FabricCheckTaskRecordPosition> getFabricCheckRecordProblemPositionList() {
        return fabricCheckRecordProblemPositionList;
    }

    public void setFabricCheckRecordProblemPositionList(List<FabricCheckTaskRecordPosition> fabricCheckRecordProblemPositionList) {
        this.fabricCheckRecordProblemPositionList = fabricCheckRecordProblemPositionList;
    }

    public FabricCheckWidth getFabricCheckRecord() {
        return fabricCheckRecord;
    }

    public void setFabricCheckRecord(FabricCheckWidth fabricCheckRecord) {
        this.fabricCheckRecord = fabricCheckRecord;
    }

    public static class FabricCheckWidth {
        private String id;
        private String sno;
        private String checkLotInfoId;
        private String lengthBefore;
        private String lengthAfter;
        private String lengthUnit;
        private String weightBefore;
        private String weightAfter;
        private String weightUnit;
        private String deliveryDate;
        private String problemCount;
        private String createTime;
        private String modifyTime;
        private String isDelete;
        private String widthTop;
        private String widthMiddle;
        private String widthBottom;
        private String fabricCheckRecordProblem;

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

        public String getCheckLotInfoId() {
            return checkLotInfoId;
        }

        public void setCheckLotInfoId(String checkLotInfoId) {
            this.checkLotInfoId = checkLotInfoId;
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

        public String getWeightBefore() {
            return weightBefore;
        }

        public void setWeightBefore(String weightBefore) {
            this.weightBefore = weightBefore;
        }

        public String getWeightAfter() {
            return weightAfter;
        }

        public void setWeightAfter(String weightAfter) {
            this.weightAfter = weightAfter;
        }

        public String getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(String weightUnit) {
            this.weightUnit = weightUnit;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getProblemCount() {
            return problemCount;
        }

        public void setProblemCount(String problemCount) {
            this.problemCount = problemCount;
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

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getWidthTop() {
            return widthTop;
        }

        public void setWidthTop(String widthTop) {
            this.widthTop = widthTop;
        }

        public String getWidthMiddle() {
            return widthMiddle;
        }

        public void setWidthMiddle(String widthMiddle) {
            this.widthMiddle = widthMiddle;
        }

        public String getWidthBottom() {
            return widthBottom;
        }

        public void setWidthBottom(String widthBottom) {
            this.widthBottom = widthBottom;
        }

        public String getFabricCheckRecordProblem() {
            return fabricCheckRecordProblem;
        }

        public void setFabricCheckRecordProblem(String fabricCheckRecordProblem) {
            this.fabricCheckRecordProblem = fabricCheckRecordProblem;
        }
    }
}
