package com.wd.winddots.desktop.presenter.view;

import com.wd.winddots.desktop.bean.DesktopListBean;

/**
 * FileName: StoreView
 * Author: 郑
 * Date: 2020/4/29 3:55 PM
 * Description: StoreView
 */
public interface StoreView {


    /*
    * 获取列表
    * */
    void getStoreListSuccess(DesktopListBean bean);
    void getStoreListError();
    void getStoreListCompleted();



}
