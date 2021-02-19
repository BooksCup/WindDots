package com.wd.winddots.message.presenter.view;

import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.GroupChatListBean;

import java.util.List;

public interface GroupChatView {
    //初始化 获取消息数据
    void initChatMessageHistorySuccess(GroupChatHistoryBean bean);
    void initChatMessageHistoryError(String errMsg);
    void initChatMessageHistoryComplete();


    // 加载更多 获取消息数据
    void loadMoreChatMessageHistorySuccess(GroupChatHistoryBean bean);
    void loadMoreChatMessageHistoryError(String errMsg);
    void loadMoreChatMessageHistoryComplete();

    // 获取群成员
    void getGroupMembersSuccess(List<GroupChatHistoryBean.AvatarMapBean> list);
    void getGroupMembersError();
    void getGroupMembersComplete();


    //发送消息
    void postMessageSuccess();
    void postMessageError();
    void postMessageComplete();

    //上传语音
    void postVoiceSuccess(String data);
    void postVoiceError(String errMsg);

    //上传图片
    void postImageSuccess(String data);
    void postImageError(String errMsg);


    // 获取底部功能按钮
    void getChatBottomItemSuccess(ChatBottomBean bean);
    void getChatBottomItemError(String errMsg);
    void getChatBottomItemErrorComplete();

}
