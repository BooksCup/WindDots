package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.PrivateChatListBean;

import java.util.List;

public interface PrivateChatTabView {

    void onPrivateChatHeaderSuccess();
    void onPrivateChatHeaderError(String errorMsg);

    void onPrivateChatListSuccess(List<PrivateChatListBean> beanList);
    void onPrivateChatListError(String errorMsg);
    void onPrivateChatListComplete();
}
