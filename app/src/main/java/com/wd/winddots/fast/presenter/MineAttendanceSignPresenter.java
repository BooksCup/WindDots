package com.wd.winddots.fast.presenter;

import okhttp3.RequestBody;

/**
 * FileName: MineAttendanceSignPresenter
 * Author: 郑
 * Date: 2020/6/2 9:43 AM
 * Description:
 */
public interface MineAttendanceSignPresenter {

    void getAttendRecord(String userId);

    void addAttendRecord(
            String recordUserId,
            String recordType,
            String recordRemark,
            String wifiName,
            String address,
            double recordLat,
            double recordLng);
}
