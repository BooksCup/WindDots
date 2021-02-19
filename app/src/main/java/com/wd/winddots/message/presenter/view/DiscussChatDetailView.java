package com.wd.winddots.message.presenter.view;

import com.wd.winddots.bean.resp.DiscussChatBean;

/**
 * FileName: DiscussChatDetailView
 * Author: 郑
 * Date: 2020/6/16 9:50 AM
 * Description:
 */
public interface DiscussChatDetailView {

    void initMessageDataListSuccess(DiscussChatBean bean);
    void initMessageDataListError();
    void initMessageDataListCompleted();

    void addUsersSuccess();
    void addUsersError();
    void addUsersCompleted();

}
