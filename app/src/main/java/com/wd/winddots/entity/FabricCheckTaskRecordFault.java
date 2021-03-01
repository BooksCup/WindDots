package com.wd.winddots.entity;

import com.wd.winddots.activity.work.PicBean;

import java.util.List;

/**
 * FileName: FabricCheckTaskLot
 * Author: 郑
 * Date: 2021/2/23 11:48 AM
 * Description:
 */
public class FabricCheckTaskRecordFault {

    private String id;
    private String lengthBefore;
    private String weightBefore;
    private List<PicBean> picBeanList;
    private boolean select = false;

    public List<PicBean> getPicBeanList() {
        return picBeanList;
    }

    public void setPicBeanList(List<PicBean> picBeanList) {
        this.picBeanList = picBeanList;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
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
