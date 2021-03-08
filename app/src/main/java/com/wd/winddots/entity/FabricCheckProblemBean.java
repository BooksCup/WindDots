package com.wd.winddots.entity;

import java.util.List;

/**
 * FileName: FabricCheckProblemBean
 * Author: 郑
 * Date: 2021/3/2 2:40 PM
 * Description:
 */
public class FabricCheckProblemBean {

    List<FabricCheckProblemSelect> fabricCheckProblemConfigList;

    List<FabricCheckTaskRecordPosition> fabricCheckRecordProblemPositionList;

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
}
