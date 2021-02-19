package com.wd.winddots.desktop.list.check.bean;

import java.util.List;

/**
 * FileName: CheckBean
 * Author: 郑
 * Date: 2021/1/15 10:50 AM
 * Description:
 */
public class CheckBean {

    private String cylinderNumber;
    private String lengthBeforeTotal;
    private String lengthAfterTotal;
    private String weightBeforeTotal;
    private String weightAfterTotal;
    private List<FabricQcRecord> fabricQcRecordList;



    public String getCylinderNumber() {
        return cylinderNumber;
    }

    public void setCylinderNumber(String cylinderNumber) {
        this.cylinderNumber = cylinderNumber;
    }

    public String getLengthBeforeTotal() {
        return lengthBeforeTotal;
    }

    public void setLengthBeforeTotal(String lengthBeforeTotal) {
        this.lengthBeforeTotal = lengthBeforeTotal;
    }

    public String getLengthAfterTotal() {
        return lengthAfterTotal;
    }

    public void setLengthAfterTotal(String lengthAfterTotal) {
        this.lengthAfterTotal = lengthAfterTotal;
    }

    public String getWeightBeforeTotal() {
        return weightBeforeTotal;
    }

    public void setWeightBeforeTotal(String weightBeforeTotal) {
        this.weightBeforeTotal = weightBeforeTotal;
    }

    public String getWeightAfterTotal() {
        return weightAfterTotal;
    }

    public void setWeightAfterTotal(String weightAfterTotal) {
        this.weightAfterTotal = weightAfterTotal;
    }

    public List<FabricQcRecord> getFabricQcRecordList() {
        return fabricQcRecordList;
    }

    public void setFabricQcRecordList(List<FabricQcRecord> fabricQcRecordList) {
        this.fabricQcRecordList = fabricQcRecordList;
    }





    public class FabricQcRecord{

        private String id;
        private String sno;
        private String cylinderNumber;
        private String lengthBefore;
        private String weightAfterTotal;
        private String lengthAfter;
        private String lengthUnit;
        private String weightBefore;
        private String weightAfter;
        private String weightUnit;
        private String createTime;
        private String modifyTime;
        private String warehouseId;
        private String remark;

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

        public String getCylinderNumber() {
            return cylinderNumber;
        }

        public void setCylinderNumber(String cylinderNumber) {
            this.cylinderNumber = cylinderNumber;
        }

        public String getLengthBefore() {
            return lengthBefore;
        }

        public void setLengthBefore(String lengthBefore) {
            this.lengthBefore = lengthBefore;
        }

        public String getWeightAfterTotal() {
            return weightAfterTotal;
        }

        public void setWeightAfterTotal(String weightAfterTotal) {
            this.weightAfterTotal = weightAfterTotal;
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

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
