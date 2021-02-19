package com.wd.winddots.fast.presenter;

/**
 * FileName: MineClaimingPresenter
 * Author: 郑
 * Date: 2020/5/4 4:40 PM
 * Description: 我的报销列表
 */
public interface MineClaimingPresenter {


    /*
    * 获取报销列表
    * */
    void getMineClaimingList(String userId,int pageNum,int numPerPage);


}
