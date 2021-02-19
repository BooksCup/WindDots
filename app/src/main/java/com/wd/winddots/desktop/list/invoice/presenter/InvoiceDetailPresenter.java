package com.wd.winddots.desktop.list.invoice.presenter;

import okhttp3.RequestBody;

/**
 * FileName: InvoiceDetailPresenter
 * Author: 郑
 * Date: 2020/7/13 2:58 PM
 * Description:
 */
public interface InvoiceDetailPresenter {



    /*
     * 获取发票详情
     * */
    void getInvoiceDetail(String id);

    /*
     * 新增发票
     * */
    void addinvoice(RequestBody body);

}
