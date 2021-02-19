package com.wd.winddots.fast.presenter;

import okhttp3.RequestBody;

/**
 * FileName: MineClaimingAddPresenter
 * Author: 郑
 * Date: 2020/5/6 5:52 PM
 * Description:
 */
public interface MineClaimingAddPresenter {

    /*
    * 获取币种列表
    * */
    void getCurrencyList(String enterpriseId);

    /*
    * 新增报销
    * */
    void addClaiming(RequestBody body,String enterpriseId);

    /*
     * 新增报销
     * */
    void updateClaiming(RequestBody body,String enterpriseId);
}
