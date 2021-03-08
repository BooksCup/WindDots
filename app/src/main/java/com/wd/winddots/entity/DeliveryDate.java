package com.wd.winddots.entity;

/**
 * FileName: DeliveryDate
 * Author: 郑
 * Date: 2021/3/5 9:44 AM
 * Description: 订单交货日期
 */
public class DeliveryDate {

    private String deliveryCount;
    private String deliveryTime;
    private String id;
    private String matchRatio;
    private String matchValue;
    private String remark;

    public String getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(String deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchRatio() {
        return matchRatio;
    }

    public void setMatchRatio(String matchRatio) {
        this.matchRatio = matchRatio;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
