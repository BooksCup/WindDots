package com.wd.winddots.message.presenter;

public interface GroupChatTabPresenter {
    void refreshGroupChatListData(String id);

    void loadMoreGroupChatListData(String id,int page);

    void outGroupPerson(String userId,String groupId);
}
