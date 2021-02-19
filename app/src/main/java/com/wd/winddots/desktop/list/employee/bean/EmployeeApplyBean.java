package com.wd.winddots.desktop.list.employee.bean;

import java.util.List;

/**
 * FileName: EmployeeApplyBean
 * Author: 郑
 * Date: 2020/11/10 11:34 AM
 * Description: 成员申请
 */
public class EmployeeApplyBean {
    private int code;
    private List<EmployeeApplyItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<EmployeeApplyItem> getData() {
        return data;
    }

    public void setData(List<EmployeeApplyItem> data) {
        this.data = data;
    }

    public static class EmployeeApplyItem{
        private String createTime;
        private int deleteStatus;
        private String enterpriseName;
        private int operateType;
        private String id;
        private String phone;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(int deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public int getOperateType() {
            return operateType;
        }

        public void setOperateType(int operateType) {
            this.operateType = operateType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
