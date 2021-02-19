package com.wd.winddots.message.presenter.view;

import com.wd.winddots.message.bean.PrivateChatListBean;

import java.util.List;

public interface PrivateChatTabView {

    void onPrivateChatHeaderSuccess();
    void onPrivateChatHeaderError(String errorMsg);

    void onPrivateChatListSuccess(List<PrivateChatListBean> beanList);
    void onPrivateChatListError(String errorMsg);
    void onPrivateChatListComplete();
}
