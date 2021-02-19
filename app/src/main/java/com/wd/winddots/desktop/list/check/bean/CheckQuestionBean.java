package com.wd.winddots.desktop.list.check.bean;

/**
 * FileName: CheckQuestionBean
 * Author: 郑
 * Date: 2021/1/15 2:57 PM
 * Description:
 */
public class CheckQuestionBean {

    private String id;
    private String tag;
    private boolean select = false;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
