package com.wd.winddots.view.selector;

/**
 * FileName: SelectBean
 * Author: 郑
 * Date: 2020/5/6 1:44 PM
 * Description:
 */
public class SelectBean {


    public SelectBean() {
        super();
    }
    public SelectBean(String name) {
        super();
        this.name = name;
    }

    public SelectBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
