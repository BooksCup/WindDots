package com.wd.winddots.desktop.list.card.bean;

import java.util.List;

/**
 * FileName: FriendSrarchBean
 * Author: 郑
 * Date: 2020/8/31 11:15 AM
 * Description:
 */
public class FriendSrarchBean {


    private List<FriendSrarchItem> data;

    public List<FriendSrarchItem> getData() {
        return data;
    }

    public void setData(List<FriendSrarchItem> data) {
        this.data = data;
    }

    public static class FriendSrarchItem{

        private String contactId;
        private String contactUserId;
        private String enterpriseName;
        private String jobName;
        private String userAvatar;
        private String userId;
        private String userName;
        private String userPhone;
        private String jobNameHl;
        private String userNameHl;
        private String enterpriseNameHl;
        private String shortName;
        private String departmentName;

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        private boolean select = false;
        private boolean disable = false;

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getJobNameHl() {
            return jobNameHl;
        }

        public void setJobNameHl(String jobNameHl) {
            this.jobNameHl = jobNameHl;
        }

        public String getUserNameHl() {
            return userNameHl;
        }

        public void setUserNameHl(String userNameHl) {
            this.userNameHl = userNameHl;
        }

        public String getEnterpriseNameHl() {
            return enterpriseNameHl;
        }

        public void setEnterpriseNameHl(String enterpriseNameHl) {
            this.enterpriseNameHl = enterpriseNameHl;
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getContactUserId() {
            return contactUserId;
        }

        public void setContactUserId(String contactUserId) {
            this.contactUserId = contactUserId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
    }

}
