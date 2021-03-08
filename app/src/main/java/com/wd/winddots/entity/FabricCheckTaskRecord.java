package com.wd.winddots.entity;

/**
 * FileName: FabricCheckTaskLot
 * Author: 郑
 * Date: 2021/2/23 11:48 AM
 * Description:
 */
public class FabricCheckTaskRecord {

    private String id;
    private String lengthBefore;
    private String weightBefore;
    private String checkLotInfoId;
    private String lengthUnit;
    private String weightAfter;
    private String lengthAfter;
    private FabricCheckProblem fabricCheckRecordProblem;
    private String problemCount;

    public String getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(String problemCount) {
        this.problemCount = problemCount;
    }

    public String getCheckLotInfoId() {
        return checkLotInfoId;
    }

    public void setCheckLotInfoId(String checkLotInfoId) {
        this.checkLotInfoId = checkLotInfoId;
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

    public String getLengthAfter() {
        return lengthAfter;
    }

    public void setLengthAfter(String lengthAfter) {
        this.lengthAfter = lengthAfter;
    }

    public FabricCheckProblem getFabricCheckRecordProblem() {
        return fabricCheckRecordProblem;
    }

    public void setFabricCheckRecordProblem(FabricCheckProblem fabricCheckRecordProblem) {
        this.fabricCheckRecordProblem = fabricCheckRecordProblem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLengthBefore() {
        return lengthBefore;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public String getWeightBefore() {
        return weightBefore;
    }

    public void setWeightBefore(String weightBefore) {
        this.weightBefore = weightBefore;
    }
}
