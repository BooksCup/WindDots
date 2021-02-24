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
