package com.wd.winddots.fast.bean;

import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: CurrencyBean
 * Author: 郑
 * Date: 2020/5/6 5:53 PM
 * Description: 币种模型
 */
public class CurrencyBean {

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
