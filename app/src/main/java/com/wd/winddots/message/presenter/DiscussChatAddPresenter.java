package com.wd.winddots.message.presenter;

import okhttp3.RequestBody;

/**
 * FileName: DiscussChatAddPresenter
 * Author: 郑
 * Date: 2020/6/9 11:49 AM
 * Description:
 */
public interface DiscussChatAddPresenter {

    void addDiscuss(RequestBody body);

    void editDiscuss(RequestBody body);

    void upLoadImage(RequestBody body);
}
