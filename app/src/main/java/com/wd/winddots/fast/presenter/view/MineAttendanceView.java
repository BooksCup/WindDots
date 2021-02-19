package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: MineAttendanceView
 * Author: 郑
 * Date: 2020/5/28 9:39 AM
 * Description:
 */
public interface MineAttendanceView {


    void getLeaveTypeListSuccess(List<SelectBean> list);
    void getLeaveTypeListError();
    void getLeaveTypeListCompleted();

    void uploadImageSuccess(UploadImageBean bean);
    void uploadImageError();
    void uploadImageCompleted();


    /*
     * 新增报销
     * */
    void addApplySuccess();
    void addApplyError();
    void addApplyCompleted();

    /*
     * 新增报销
     * */
    void updateApplySuccess();
    void updateApplyError();
    void updateApplyCompleted();
}
