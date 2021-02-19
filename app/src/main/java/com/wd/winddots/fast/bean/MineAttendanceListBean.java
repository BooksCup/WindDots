package com.wd.winddots.fast.bean;

import com.haibin.calendarview.Calendar;

import java.util.List;

/**
 * FileName: MineAttendanceBean
 * Author: 郑
 * Date: 2020/5/27 11:06 AM
 * Description:
 */
public class MineAttendanceListBean {

    private String code;
    private int totalCount;
    private List<MineAttendanceBean> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MineAttendanceBean> getList() {
        return list;
    }

    public void setList(List<MineAttendanceBean> list) {
        this.list = list;
    }

    public static class MineAttendanceBean{
        private String amount;
        private String applyStatus;
        private String applyType;
        private String approvalStatus;
        private String cause;
        private String createdBy;
        private List<MineAttendanceDetailBean> detailList;
        private String enterpriseId;
        private String leaveDays;
        private String type;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getApplyType() {
            return applyType;
        }

        public void setApplyType(String applyType) {
            this.applyType = applyType;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public List<MineAttendanceDetailBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<MineAttendanceDetailBean> detailList) {
            this.detailList = detailList;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


    public static class MineAttendanceDetailBean{
        private String endTime;
        private String startTime;



        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }


}
