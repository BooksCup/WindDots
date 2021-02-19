package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.fast.bean.ApplyDetailBean;

/**
 * FileName: MineClaimingDetailView
 * Author: 郑
 * Date: 2020/5/5 2:07 PM
 * Description:
 */
public interface MineClaimingDetailView {


    /*
    * 获取报销详情结果
    * */
    void getClaimingDetailSuccess(ApplyDetailBean bean);
    void getClaimingDetailError(String error);
    void getClaimingDetailCompleted();

    /*
     * 获取报销详情结果
     * */
    void recallApplySuccess();
    void recallApplyError();
    void recallApplyDetailCompleted();
}
