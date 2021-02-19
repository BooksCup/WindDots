package com.wd.winddots.message.presenter.view;

import com.wd.winddots.message.bean.ExamineApproveBean;

public interface ExamineApproveView {
    void onExamineApproveListRefreshSuccess(ExamineApproveBean bean);
    void onExamineApproveListRefreshError(String errorMsg);
    void onExamineApproveListRefreshComplete();

    void onExamineApproveListLoadMoreSuccess(ExamineApproveBean bean);
    void onExamineApproveListLoadMoreError(String errorMsg);
    void onExamineApproveListLoadMoreComplete();
}
