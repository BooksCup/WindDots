package com.wd.winddots.message.presenter.view;

import com.wd.winddots.components.UploadImageBean;

/**
 * FileName: DiscussChatAddView
 * Author: 郑
 * Date: 2020/6/9 11:50 AM
 * Description:
 */
public interface DiscussChatAddView {


    void addDiscussSuccess();
    void addDiscussError();
    void addDiscussComplete();

    void editDiscussSuccess();
    void editDiscussError();
    void editDiscussComplete();

    void uploadImageSuccess(UploadImageBean bean);
    void uploadImageError();
    void uploadImageCompleted();

}
