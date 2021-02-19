package com.wd.winddots.fast.bean;

import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: LeaveTypeBean
 * Author: 郑
 * Date: 2020/5/28 11:14 AM
 * Description: 请假类型
 */
public class LeaveTypeBean {

    private int code;
    private List<SelectBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SelectBean> getData() {
        return data;
    }

    public void setData(List<SelectBean> data) {
        this.data = data;
    }
}
