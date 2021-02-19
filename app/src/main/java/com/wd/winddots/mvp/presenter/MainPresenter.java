package com.wd.winddots.mvp.presenter;

import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.mvp.view.MainView;

import static com.wd.winddots.confifg.Config.TAB_BUSINESSAREA;
import static com.wd.winddots.confifg.Config.TAB_DESKTOP;
import static com.wd.winddots.confifg.Config.TAB_MESSAGE;
import static com.wd.winddots.confifg.Config.TAB_SCHEDULE;
import static com.wd.winddots.confifg.Config.TAB_SHORTCUT;

public class MainPresenter extends BasePresenter<MainView> {



    public MainPresenter() {
    }

    public void switchNavigation(int id) {
        final MainView view = getView();
        if (view == null) return;
        switch (id){
            case R.id.activity_main_tab_businessarea:
                view.switch2businessarea(TAB_BUSINESSAREA);
                break;
            case R.id.activity_main_tab_desktop:
                view.switch2desktop(TAB_DESKTOP);
                break;
            case R.id.activity_main_tab_message:
                getView().switch2message(TAB_MESSAGE);
                break;
            case R.id.activity_main_tab_schedule:
                view.switch2schedule(TAB_SCHEDULE);
                break;
            case R.id.activity_main_tab_bottom_bar_shortcut:
                view.switch2shortcut(TAB_SHORTCUT);
                break;
        }

    }
}
