package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.ApprovalProcessBean;

public interface ApprovalProcessView {
    // 初始化数据
    void onLoadSuccess(ApprovalProcessBean bean);
    void onLoadError(String errMsg);
    void onLoadComplete();


    // (审批)同意或驳回
    void onExamineApproveSuccess(String data);
    void onExamineApproveError(String errMsg);
    void onExamineApproveComplete();


    // 撤销申请
    void onRecallApprovalSuccess(String data);
    void onRecallApprovalError(String errMsg);
    void onRecallApprovalComplete();
}
