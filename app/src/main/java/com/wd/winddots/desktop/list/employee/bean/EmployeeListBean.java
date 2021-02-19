package com.wd.winddots.desktop.list.employee.bean;

import java.util.List;

/**
 * FileName: EmployeeListBean
 * Author: 郑
 * Date: 2020/11/10 9:33 AM
 * Description: 人员列表
 */
public class EmployeeListBean {

    private int code;
    private int totalCount;
    private List<EmployeeItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<EmployeeItem> getData() {
        return data;
    }

    public void setData(List<EmployeeItem> data) {
        this.data = data;
    }

    public class EmployeeItem{
        private String avatar;
        private String departmentId;
        private String departmentName;
        private String enterpriseId;
        private int gender;
        private String jobName;
        private String jobNo;
        private int jobStatus;
        private String name;
        private String phone;
        private int userStatus;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getJobNo() {
            return jobNo;
        }

        public void setJobNo(String jobNo) {
            this.jobNo = jobNo;
        }

        public int getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
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

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }
    }

}
