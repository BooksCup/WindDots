package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

/**
 * FileName: MineClaimingAddView
 * Author: 郑
 * Date: 2020/5/6 5:53 PM
 * Description:
 */
public interface MineClaimingAddView {

    /*
    * 获取币种 list
    * */
    void getCurrencyListSuccess(List<SelectBean> currencyList);
    void getCurrencyListerror();
    void getCurrencyListCompleted();


    /*
     * 新增报销
     * */
    void addClaimingtSuccess();
    void addClaimingError();
    void addClaimingCompleted();

    /*
     * 新增报销
     * */
    void updateClaimingtSuccess();
    void updateClaimingError();
    void updateClaimingCompleted();
}
