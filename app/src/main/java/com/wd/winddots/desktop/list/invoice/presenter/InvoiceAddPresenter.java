package com.wd.winddots.desktop.list.invoice.presenter;

import okhttp3.RequestBody;

/**
 * FileName: InvoiceAddPresenter
 * Author: 郑
 * Date: 2020/7/9 10:02 AM
 * Description:
 */
public interface InvoiceAddPresenter {

    /*
    * 上传图片
    * */
    void upLoadImage(RequestBody body,int index);

    /*
    * 获取币种列表
    * */
    void getCurrencyList(String enterpriseId);

    /*
    * 获取费用类型列表
    * */
    void getCostTypeList(String enterpriseId);

    /*
     * 新增发票
     * */
    void addinvoice(RequestBody body);
}
