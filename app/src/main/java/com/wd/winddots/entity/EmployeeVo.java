package com.wd.winddots.entity;

public class EmployeeVo {

    private Integer applyNum;
    private PageInfo<User> userPageInfo;

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public PageInfo<User> getUserPageInfo() {
        return userPageInfo;
    }

    public void setUserPageInfo(PageInfo<User> userPageInfo) {
        this.userPageInfo = userPageInfo;
    }
}
