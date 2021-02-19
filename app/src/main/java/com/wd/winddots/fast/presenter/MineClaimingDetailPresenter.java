package com.wd.winddots.fast.presenter;

/**
 * FileName: MineClaimingDetailPresenter
 * Author: 郑
 * Date: 2020/5/5 2:04 PM
 * Description:
 */
public interface MineClaimingDetailPresenter {

    /*
    * 获取报销详情
    * */
    void getMineClaimingDetail(String id,String userId);


    /*
     * 撤回报销
     * */
    void recallApply(String id);

}
