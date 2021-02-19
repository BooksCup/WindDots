package com.wd.winddots.message.presenter.view;

import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;

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

    //上传语音
    void postVoiceSuccess(String data);
    void postVoiceError(String errMsg);


    // 获取底部功能按钮
    void getChatBottomItemSuccess(ChatBottomBean bean);
    void getChatBottomItemError(String errMsg);
    void getChatBottomItemErrorComplete();



}
