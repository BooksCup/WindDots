package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.fast.bean.MineAttendanceSignBean;

/**
 * FileName: MineAttendanceSignView
 * Author: 郑
 * Date: 2020/6/2 9:45 AM
 * Description:
 */
public interface MineAttendanceSignView {

    /*
    * 获取当天考勤记录
    * */
    void getAttendRecordSuccess(MineAttendanceSignBean bean);

    void getAttendRecordError();

    void getAttendRecordCompleted();


    /*
    * 打卡
    * */
    void addAttendRecordSuccess();
    void addAttendRecordError();
    void addAttendRecordCompleted();

}
