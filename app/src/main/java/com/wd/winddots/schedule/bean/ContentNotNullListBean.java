package com.wd.winddots.schedule.bean;

import java.util.List;

/**
 * FileName: ContentNotNullListBean
 * Author: 郑
 * Date: 2020/6/5 3:25 PM
 * Description:
 */
public class ContentNotNullListBean {


    private int code;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
