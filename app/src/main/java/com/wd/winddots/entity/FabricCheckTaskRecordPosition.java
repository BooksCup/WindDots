package com.wd.winddots.entity;

import java.util.List;

/**
 * FileName: FabricCheckTaskLot
 * Author: 郑
 * Date: 2021/2/23 11:48 AM
 * Description:
 */
public class FabricCheckTaskRecordPosition {

    private String id;
    private String position;
    private List<FabricCheckTaskRecordFault> recordFaultList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<FabricCheckTaskRecordFault> getRecordFaultList() {
        return recordFaultList;
    }

    public void setRecordFaultList(List<FabricCheckTaskRecordFault> recordFaultList) {
        this.recordFaultList = recordFaultList;
    }
}
