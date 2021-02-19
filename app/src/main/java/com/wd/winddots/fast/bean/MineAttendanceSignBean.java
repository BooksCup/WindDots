package com.wd.winddots.fast.bean;

import java.util.List;

/**
 * FileName: MineAttendanceSignBean
 * Author: 郑
 * Date: 2020/6/2 9:55 AM
 * Description:
 */
public class MineAttendanceSignBean {
    private String ceode;
    private String workStatus;
    private String outStatus;
    private String outWorkStatus;
    private List<AttendRecordList> attendRecordList;

    public String getCeode() {
        return ceode;
    }

    public void setCeode(String ceode) {
        this.ceode = ceode;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getOutWorkStatus() {
        return outWorkStatus;
    }

    public void setOutWorkStatus(String outWorkStatus) {
        this.outWorkStatus = outWorkStatus;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public List<AttendRecordList> getAttendRecordList() {
        return attendRecordList;
    }

    public void setAttendRecordList(List<AttendRecordList> attendRecordList) {
        this.attendRecordList = attendRecordList;
    }

    public class AttendRecordList{

        private String recordAddressName;
        private String recordEnterpriseId;
        private String recordId;
        private String workStatus;
        private String recordRemark;
        private String recordSettingId;
        private String ceode;
        private String recordTime;
        private String recordType;
        private String recordUserId;
        private String recordWifiName;
        private double recordLat;
        private double recordLng;

        public String getRecordAddressName() {
            return recordAddressName;
        }

        public void setRecordAddressName(String recordAddressName) {
            this.recordAddressName = recordAddressName;
        }

        public String getRecordEnterpriseId() {
            return recordEnterpriseId;
        }

        public void setRecordEnterpriseId(String recordEnterpriseId) {
            this.recordEnterpriseId = recordEnterpriseId;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public String getRecordRemark() {
            return recordRemark;
        }

        public void setRecordRemark(String recordRemark) {
            this.recordRemark = recordRemark;
        }

        public String getRecordSettingId() {
            return recordSettingId;
        }

        public void setRecordSettingId(String recordSettingId) {
            this.recordSettingId = recordSettingId;
        }

        public String getCeode() {
            return ceode;
        }

        public void setCeode(String ceode) {
            this.ceode = ceode;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public String getRecordType() {
            return recordType;
        }

        public void setRecordType(String recordType) {
            this.recordType = recordType;
        }

        public String getRecordUserId() {
            return recordUserId;
        }

        public void setRecordUserId(String recordUserId) {
            this.recordUserId = recordUserId;
        }

        public String getRecordWifiName() {
            return recordWifiName;
        }

        public void setRecordWifiName(String recordWifiName) {
            this.recordWifiName = recordWifiName;
        }

        public double getRecordLat() {
            return recordLat;
        }

        public void setRecordLat(double recordLat) {
            this.recordLat = recordLat;
        }

        public double getRecordLng() {
            return recordLng;
        }

        public void setRecordLng(double recordLng) {
            this.recordLng = recordLng;
        }
    }

}
