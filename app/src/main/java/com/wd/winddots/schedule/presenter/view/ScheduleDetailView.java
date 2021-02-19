package com.wd.winddots.schedule.presenter.view;

import com.wd.winddots.bean.resp.ScheduleBena;

/**
 * FileName: ScheduleDetailView
 * Author: 郑
 * Date: 2020/6/4 11:50 AM
 * Description:
 */
public interface ScheduleDetailView {


    void deleteScheduleSuccess();
    void deleteScheduleError();
    void deleteScheduleCompleted();


    void getScheduleDetailSuccess(ScheduleBena bena);
    void getScheduleDetailError();
    void getScheduleDetailCompleted();

    void addScheduleSuccess();
    void addScheduleError();
    void addScheduleCompleted();

    void updateScheduleSuccess();
    void updateScheduleError();
    void updateScheduleCompleted();

}
