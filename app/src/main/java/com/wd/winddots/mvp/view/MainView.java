package com.wd.winddots.mvp.view;

import com.wd.winddots.base.BaseView;

public interface MainView extends BaseView {
    void switch2businessarea(int id);//商圈
    void switch2desktop(int id);//桌面
    void switch2message(int id);//消息
    void switch2schedule(int id);//日程
    void switch2shortcut(int id);//快捷
}
