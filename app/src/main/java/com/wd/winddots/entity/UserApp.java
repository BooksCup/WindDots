package com.wd.winddots.entity;

import java.util.List;

public class UserApp {

    private List<App> userAppList;
    private List<App> userFastAppList;

    public List<App> getUserAppList() {
        return userAppList;
    }

    public void setUserAppList(List<App> userAppList) {
        this.userAppList = userAppList;
    }

    public List<App> getUserFastAppList() {
        return userFastAppList;
    }

    public void setUserFastAppList(List<App> userFastAppList) {
        this.userFastAppList = userFastAppList;
    }

}
