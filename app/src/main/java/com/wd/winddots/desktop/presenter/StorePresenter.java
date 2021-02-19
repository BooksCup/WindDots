package com.wd.winddots.desktop.presenter;

/**
 * FileName: StorePresenter
 * Author: 郑
 * Date: 2020/4/29 3:50 PM
 * Description: 办公应用列表工具类
 */
public interface StorePresenter {

    /*
    * 获取应用列表
    * */
   void getStoreList(String userId,int pageNum,int numPerPage);


}
