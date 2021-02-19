package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.message.bean.ChatBottomBean;

public interface DiscussChatView {

    /*
    * 获取会务详情
    * */
    void initMessageDataListSuccess(DiscussChatBean bean);
    void initMessageDataListError(String errMsg);
    void initMessageDataListComplete();



    /*
    * 添加评论
    * */
    void senMessageSuccess();
    void senMessageError();
    void senMessageComplete();

    /*
     * 删除会务
     * */
    void deleteDiscussSuccess();
    void deleteDiscussError();
    void deleteDiscussComplete();

    /*
     * 更改会务状态
     * */
    void updateDiscussSuccess();
    void updateDiscussError();
    void updateDiscussComplete();


    // 获取底部功能按钮
    void getChatBottomItemSuccess(ChatBottomBean bean);
    void getChatBottomItemError(String errMsg);
    void getChatBottomItemErrorComplete();




}
