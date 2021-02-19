package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.components.UploadImageBean;

/**
 * FileName: ClaimingExpenseDetailView
 * Author: 郑
 * Date: 2020/5/20 3:29 PM
 * Description:
 */
public interface ClaimingExpenseDetailView {

    void updateApplyInvoiceTypeSuccess();
    void updateApplyInvoiceTypeError();
    void updateApplyInvoiceTypeCompleted();


    void updateApplyInvoiceImageSuccess();
    void updateApplyInvoiceImageError();
    void updateApplyInvoiceImageCompleted();


    void uploadImageSuccess(UploadImageBean bean,int position);
    void uploadImageError();
    void uploadImageCompleted();
}
