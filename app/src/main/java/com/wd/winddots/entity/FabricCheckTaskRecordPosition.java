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
    private String problemPosition;
    private List<FabricCheckProblem> fabricCheckRecordProblemList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemPosition() {
        return problemPosition;
    }

    public void setProblemPosition(String problemPosition) {
        this.problemPosition = problemPosition;
    }

    public List<FabricCheckProblem> getFabricCheckRecordProblemList() {
        return fabricCheckRecordProblemList;
    }

    public void setFabricCheckRecordProblemList(List<FabricCheckProblem> fabricCheckRecordProblemList) {
        this.fabricCheckRecordProblemList = fabricCheckRecordProblemList;
    }
}
