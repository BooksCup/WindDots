package com.wd.winddots.mvp.presenter;

public interface ExamineApprovePresenter {
    void refreshExamineApproveListData(String id);

    void loadMoreExamineApproveListData(String id,int page);

    void changeApplyReadStatus(String applyId);
}
