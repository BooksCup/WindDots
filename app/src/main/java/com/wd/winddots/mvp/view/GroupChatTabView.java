package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.GroupChatListBean;

import java.util.List;

public interface GroupChatTabView {

    void onGroupChatListRefreshSuccess(GroupChatListBean bean);
    void onGroupChatListRefreshError(String errorMsg);
    void onGroupChatListRefreshComplete();

    void onGroupChatListLoadMoreSuccess(GroupChatListBean bean);
    void onGroupChatListLoadMoreError(String errorMsg);
    void onGroupChatListLoadMoreComplete();
}
