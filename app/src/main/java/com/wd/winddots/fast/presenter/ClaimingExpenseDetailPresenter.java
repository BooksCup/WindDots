package com.wd.winddots.fast.presenter;

import okhttp3.RequestBody;

/**
 * FileName: ClaimingExpenseDetailPresenter
 * Author: 郑
 * Date: 2020/5/20 3:27 PM
 * Description:
 */
public interface ClaimingExpenseDetailPresenter {
    /*
    * 更改发票状态
    * */
    void updateApplyInvoiceType(String applyId,String applyAddressId,String invoiceType);


    /*
    * 更改发票照片
    * */
    void updateApplyInvoiceImage(String applyId, RequestBody requestBody);

    /*
    * 上传照片
    * */
    void uploadImage(RequestBody body,int position);


}
