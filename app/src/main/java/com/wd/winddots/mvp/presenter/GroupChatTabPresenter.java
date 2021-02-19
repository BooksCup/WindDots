package com.wd.winddots.mvp.presenter;

public interface GroupChatTabPresenter {
    void refreshGroupChatListData(String id);

    void loadMoreGroupChatListData(String id,int page);
}
