package com.wd.winddots.entity;

/**
 * FileName: FabricCheckProblemSelect
 * Author: 郑
 * Date: 2021/3/2 2:38 PM
 * Description: 盘点问题选择
 */
public class FabricCheckProblemSelect {

    private String id;
    private String enterpriseId;
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
