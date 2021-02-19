package com.wd.winddots.schedule.presenter;

import okhttp3.RequestBody;

/**
 * FileName: ScheduleDetailPresenter
 * Author: 郑
 * Date: 2020/6/4 11:49 AM
 * Description:
 */
public interface ScheduleDetailPresenter {


    /*
    * 获取日程详情
    * */
    void getScheduleDetail(String userId,String scheduleId);

    /*
    * 删除日程
    * */
    void deleteScheduleDetail(String scheduleId,String userId);

    /*
    * 新建日程
    * */
    void addSchedule(RequestBody requestBody);

    /*
    * 编辑日程
    * */
    void updateSchedule(RequestBody requestBody);



}
