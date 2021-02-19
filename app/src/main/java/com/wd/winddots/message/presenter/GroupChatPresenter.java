package com.wd.winddots.message.presenter;

import okhttp3.RequestBody;

public interface GroupChatPresenter {
    void initMessage(String fromId,String convrId,long jCreateTime);

    void getGroupMembers(String groupId);

    void loadMoreMessage(String fromId,String convrId,long jCreateTime);

    void sendMessage(String fromId,String targetId,String body,String targetType,String msgType);

    void postText(String fromId,String targetId,String text,String targetType,String msgType);

    void postImage(RequestBody body, String fromId, String targetId);

    void leaveGroup(String fromId, String convrId);

    void postVoice( RequestBody body,String fromId,String targetId,int timeLong);

    void getChatBottomItem(String userId,int module);

    void chatBottomItemSort(String userId,RequestBody body);

    void atMembers(String userId,String groupId,RequestBody body);

}
