package com.wd.winddots.fast.presenter.view;

import com.wd.winddots.fast.bean.ClaimingListBean;

/**
 * FileName: MineClaimingView
 * Author: 郑
 * Date: 2020/5/5 10:41 AM
 * Description:
 */
public interface MineClaimingView {

    void getClaimingListSuccess(ClaimingListBean bean);
    void getClaimingListError(String error);
    void getClaimingListCompleted();


}
