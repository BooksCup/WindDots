package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.fast.bean.ApplyDetailBean;

/**
 * FileName: MineAttendanceDetailView
 * Author: 郑
 * Date: 2020/5/29 12:14 PM
 * Description:
 */
public interface MineAttendanceDetailView {

    /*
     * 获取报销详情结果
     * */
    void getApplyDetailSuccess(ApplyDetailBean bean);
    void getApplyDetailError(String error);
    void getApplyDetailCompleted();

    /*
     * 获取报销详情结果
     * */
    void recallApplySuccess();
    void recallApplyError();
    void recallApplyDetailCompleted();
}
