package com.wd.winddots.desktop.list.invoice.presenter.view;

import com.wd.winddots.desktop.list.invoice.bean.InvoiceDetailBean;

/**
 * FileName: InvoiceDetailView
 * Author: 郑
 * Date: 2020/7/13 3:00 PM
 * Description:
 */
public interface InvoiceDetailView {

    void getInvoiceDetailSuccess(InvoiceDetailBean invoiceDetailBean);
    void getInvoiceDetailError();
    void getInvoiceDetailComplete();

    void addInvoiceSuccess();
    void addInvoiceError();
    void addInvoiceComplete();

}
