package com.wd.winddots.desktop.list.invoice.bean;

/**
 * FileName: InvoiceDetailBean
 * Author: 郑
 * Date: 2020/7/7 10:02 AM
 * Description:
 */
public class InvoiceDetailBean {

    private int code;
    private String msg;
    private InvoiceListGroupBean.InvoiceDetail data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InvoiceListGroupBean.InvoiceDetail getData() {
        return data;
    }

    public void setData(InvoiceListGroupBean.InvoiceDetail data) {
        this.data = data;
    }
}
