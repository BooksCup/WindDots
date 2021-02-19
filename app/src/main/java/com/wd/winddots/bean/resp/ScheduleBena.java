package com.wd.winddots.bean.resp;

import java.util.List;

/**
 * FileName: ScheduleBena
 * Author: 郑
 * Date: 2020/6/3 10:46 AM
 * Description:
 */
public class ScheduleBena {

    private String isUser;
    private String scheduleContent;
    private String scheduleCreateTime;
    private String scheduleCreateUserId;
    private String scheduleDate;
    private String scheduleEndTime;
    private String scheduleId;
    private String scheduleIsAllDay;
    private String scheduleIsDelete;
    private String scheduleStartTime;
    private String scheduleTitle;
    private List<User> userList;

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String getScheduleCreateTime() {
        return scheduleCreateTime;
    }

    public void setScheduleCreateTime(String scheduleCreateTime) {
        this.scheduleCreateTime = scheduleCreateTime;
    }

    public String getScheduleCreateUserId() {
        return scheduleCreateUserId;
    }

    public void setScheduleCreateUserId(String scheduleCreateUserId) {
        this.scheduleCreateUserId = scheduleCreateUserId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleIsAllDay() {
        return scheduleIsAllDay;
    }

    public void setScheduleIsAllDay(String scheduleIsAllDay) {
        this.scheduleIsAllDay = scheduleIsAllDay;
    }

    public String getScheduleIsDelete() {
        return scheduleIsDelete;
    }

    public void setScheduleIsDelete(String scheduleIsDelete) {
        this.scheduleIsDelete = scheduleIsDelete;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public class User{
        private String avatar;
        private String id;
        private String name;
        private String phone;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
