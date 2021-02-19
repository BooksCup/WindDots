package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.PrivateChatHistoryBean;

public interface PrivateChatView {
    //初始化 获取消息数据
    void initChatMessageHistorySuccess(PrivateChatHistoryBean bean);
    void initChatMessageHistoryError(String errMsg);
    void initChatMessageHistoryComplete();


    // 加载更多 获取消息数据
    void loadMoreChatMessageHistorySuccess(PrivateChatHistoryBean bean);
    void loadMoreChatMessageHistoryError(String errMsg);
    void loadMoreChatMessageHistoryComplete();


    //发送消息
    void postMessageSuccess(String msgType);
    void postMessageError(String msgType,String errMsg);
    void postMessageComplete();


    //上传图片
    void postImageSuccess(String data);
    void postImageError(String errMsg);




}
