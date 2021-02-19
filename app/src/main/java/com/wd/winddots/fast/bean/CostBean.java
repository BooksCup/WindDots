package com.wd.winddots.fast.bean;

import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: CostBean
 * Author: 郑
 * Date: 2020/5/7 11:33 AM
 * Description: 费用类型模型
 */
public class CostBean {

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
