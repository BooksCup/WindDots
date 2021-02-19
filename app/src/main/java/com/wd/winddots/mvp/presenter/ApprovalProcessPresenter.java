package com.wd.winddots.mvp.presenter;

public interface ApprovalProcessPresenter {
    // 初始化数据
    void loadData(String id,String userId,String type);

    // 审批 同意或驳回
    void examineApprove(String userId,String applyId,String applyStatus,String applyRemark,String enterpriseId);

    // 撤销
    void recallApproval(String userId,String id);
}
