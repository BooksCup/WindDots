package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.GroupChatHistoryBean;

public interface GroupChatView {
    //初始化 获取消息数据
    void initChatMessageHistorySuccess(GroupChatHistoryBean bean);
    void initChatMessageHistoryError(String errMsg);
    void initChatMessageHistoryComplete();


    // 加载更多 获取消息数据
    void loadMoreChatMessageHistorySuccess(GroupChatHistoryBean bean);
    void loadMoreChatMessageHistoryError(String errMsg);
    void loadMoreChatMessageHistoryComplete();


    //发送消息
    void postMessageSuccess(String msgType);
    void postMessageError(String msgType,String errMsg);
    void postMessageComplete();
}
