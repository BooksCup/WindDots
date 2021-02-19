package com.wd.winddots.fast.presenter;

/**
 * FileName: MineAttendanceDetailPresenter
 * Author: 郑
 * Date: 2020/5/29 12:13 PM
 * Description:
 */
public interface MineAttendanceDetailPresenter {

    void getApplyDetail(String id,String userId);

    /*
     * 撤回报销
     * */
    void recallApply(String id);
}
