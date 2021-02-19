package com.wd.winddots.desktop.presenter.view;

import com.wd.winddots.desktop.bean.DesktopListBean;

public interface WorkView {


    /*
    * 获取列表数据
    * */
    void getWorkListSuccess(DesktopListBean listBean);
    void getWorkListError(Throwable e);
    void getWorkListCompleted();

    /*
    * 删除 item
    * */

    void deleteItemSuccess();
    void deleteItemError(String s);
    void deleteItemCompleted();

    /*
     * 排序 item
     * */
    void setApplicationSortSuccess();
    void setApplicationSortError(String s);
    void setApplicationSortCompleted();


}
