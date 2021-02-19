package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.ExamineApproveBean;
import com.wd.winddots.bean.resp.GroupChatListBean;

public interface ExamineApproveView {
    void onExamineApproveListRefreshSuccess(ExamineApproveBean bean);
    void onExamineApproveListRefreshError(String errorMsg);
    void onExamineApproveListRefreshComplete();

    void onExamineApproveListLoadMoreSuccess(ExamineApproveBean bean);
    void onExamineApproveListLoadMoreError(String errorMsg);
    void onExamineApproveListLoadMoreComplete();
}
