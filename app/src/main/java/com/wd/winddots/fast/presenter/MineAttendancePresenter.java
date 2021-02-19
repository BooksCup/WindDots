package com.wd.winddots.fast.presenter;

import okhttp3.RequestBody;

/**
 * FileName: MineAttendancePresenter
 * Author: 郑
 * Date: 2020/5/28 9:37 AM
 * Description: 请假 加班  公出
 */
public interface MineAttendancePresenter {

    /*
    * 获取请假类型列表
    * */
    void getLeaveTypeList(String enterpriseId);

    void upLoadImage(RequestBody body);

    void addApply(RequestBody body,String enterpriseId);

    void updateApply(RequestBody body,String enterpriseId);
}
