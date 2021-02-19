package com.wd.winddots.message.presenter;

public interface PrivateChatTabPresenter {
    void loadHeaderData();//加载私聊上面头部列表数据
    void loadListData(String fromId);// 加载私聊下面聊天列表数据
}
