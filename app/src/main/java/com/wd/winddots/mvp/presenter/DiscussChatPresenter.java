package com.wd.winddots.mvp.presenter;

import okhttp3.RequestBody;

public interface DiscussChatPresenter {
    void initMessage(String id,String userId);

    void sendMessage(RequestBody body);

    void deleteDiscuss(String id);

    void updateDiscussStatus(String id,int questionStatus);

    void getChatBottomItem(String userId,int module);

    void chatBottomItemSort(String userId,RequestBody body);
}
