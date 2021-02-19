package com.wd.winddots.message.presenter;

import java.util.List;

/**
 * FileName: DiscussChatDetailPresenter
 * Author: 郑
 * Date: 2020/6/16 9:47 AM
 * Description:
 */
public interface DiscussChatDetailPresenter {

    void initMessage(String id, String userId);

    void addUsers(String id, List<String> userIds);

}
