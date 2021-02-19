package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.fast.bean.CostBean;

/**
 * FileName: MineClaimingAddDetailView
 * Author: 郑
 * Date: 2020/5/12 2:30 PM
 * Description:
 */
public interface MineClaimingAddDetailView {


    void getCostTypsSuccess(CostBean bean);
    void getCostTypsError();
    void getCostTypsCompleted();


    void uploadImageSuccess(UploadImageBean bean);
    void uploadImageError();
    void uploadImageCompleted();

}
