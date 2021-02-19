package com.wd.winddots.desktop.list.invoice.presenter.view;

import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: InvoiceAddView
 * Author: 郑
 * Date: 2020/7/9 10:03 AM
 * Description:
 */
public interface InvoiceAddView {

    void getCurrencyListSuccess(List<SelectBean> list);
    void getCurrencyListerror();
    void getCurrencyListComplete();

    void getCostTypeListSuccess(List<SelectBean> list);
    void getCostTypeListError();
    void getCostTypeListComplete();


    void uploadImageSuccess(UploadImageBean bean,int index);
    void uploadImageError();
    void uploadImageCompleted();


    void addInvoiceSuccess();
    void addInvoiceError();
    void addInvoiceComplete();
}
