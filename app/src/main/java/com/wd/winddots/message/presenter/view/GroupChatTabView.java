package com.wd.winddots.message.presenter.view;

import com.wd.winddots.message.bean.GroupChatListBean;

public interface GroupChatTabView {

    void onGroupChatListRefreshSuccess(GroupChatListBean bean);
    void onGroupChatListRefreshError(String errorMsg);
    void onGroupChatListRefreshComplete();

    void onGroupChatListLoadMoreSuccess(GroupChatListBean bean);
    void onGroupChatListLoadMoreError(String errorMsg);
    void onGroupChatListLoadMoreComplete();
}
