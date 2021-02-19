package com.wd.winddots.fast.bean;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: ClaimingDetailBean
 * Author: 郑
 * Date: 2020/5/5 1:48 PM
 * Description: 报销详情对象
 */
public class ApplyDetailBean implements Serializable {

    private String accountBank;
    private String accountName;
    private String amount;
    private String applyStatus;
    private String approvalStatus;
    private String avatar;
    private List<User> copyUsers;
    private String createdBy;
    private String createdTime;
    private String currency;
    private List<ClaimingModel> detailList;
    private String enterpriseId;
    private String id;
    private String jobName;
    private String modifyTime;
    private String payType;
    private String remark;
    private String status;
    private String title;
    private String toEnterpriseAccountId;
    private String toEnterpriseId;
    private String toEnterpriseName;
    private String toEnterpriseShortName;
    private String type;
    private List<User> users;
    private String userName;
    private String voucher;
    private String leaveType;
    private String leaveDays;
    private String cause;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<User> getCopyUsers() {
        return copyUsers;
    }

    public void setCopyUsers(List<User> copyUsers) {
        this.copyUsers = copyUsers;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ClaimingModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ClaimingModel> detailList) {
        this.detailList = detailList;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToEnterpriseAccountId() {
        return toEnterpriseAccountId;
    }

    public void setToEnterpriseAccountId(String toEnterpriseAccountId) {
        this.toEnterpriseAccountId = toEnterpriseAccountId;
    }

    public String getToEnterpriseId() {
        return toEnterpriseId;
    }

    public void setToEnterpriseId(String toEnterpriseId) {
        this.toEnterpriseId = toEnterpriseId;
    }

    public String getToEnterpriseName() {
        return toEnterpriseName;
    }

    public void setToEnterpriseName(String toEnterpriseName) {
        this.toEnterpriseName = toEnterpriseName;
    }

    public String getToEnterpriseShortName() {
        return toEnterpriseShortName;
    }

    public void setToEnterpriseShortName(String toEnterpriseShortName) {
        this.toEnterpriseShortName = toEnterpriseShortName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class User implements Serializable{
        private String addApprovaler;
        private String approvalStatus;
        private String approvalTime;
        private String avatar;
        private String enterpriseId;
        private String id;
        private String jobName;
        private String sort;
        private String status;
        private String type;
        private String userId;
        private String userName;
        private String approvalRemark;

        public String getApprovalRemark() {
            return approvalRemark;
        }

        public void setApprovalRemark(String approvalRemark) {
            this.approvalRemark = approvalRemark;
        }



        public String getAddApprovaler() {
            return addApprovaler;
        }

        public void setAddApprovaler(String addApprovaler) {
            this.addApprovaler = addApprovaler;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalTime() {
            return approvalTime;
        }

        public void setApprovalTime(String approvalTime) {
            this.approvalTime = approvalTime;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }


    public static class ClaimingModel implements Serializable{
        private String remark;
        private String amount;
        private String costName;
        private String costType;
        private String createTime;
        private String id;
        private String startTime;
        private String endTime;
        private List<ClaimingDetail> addresses;
        private boolean isSelect = true;
        private String deptId;
        private String deptName;

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCostName() {
            return costName;
        }

        public void setCostName(String costName) {
            this.costName = costName;
        }

        public String getCostType() {
            return costType;
        }

        public void setCostType(String costType) {
            this.costType = costType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ClaimingDetail> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<ClaimingDetail> addresses) {
            this.addresses = addresses;
        }
    }



    public static class ClaimingDetail implements Serializable{
        private String amount;
        private String applyDetailId;
        private String createTime;
        private String invoice;
        private List<Invoice> invoices;
        private String id;
        private String projectTitle;
        private String reachTime;
        private String time;
        private String invoiceText;

        public String getInvoiceText() {
            return invoiceText;
        }

        public void setInvoiceText(String invoiceText) {
            this.invoiceText = invoiceText;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApplyDetailId() {
            return applyDetailId;
        }

        public void setApplyDetailId(String applyDetailId) {
            this.applyDetailId = applyDetailId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public List<Invoice> getInvoices() {
            return invoices;
        }

        public void setInvoices(List<Invoice> invoices) {
            this.invoices = invoices;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProjectTitle() {
            return projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        public String getReachTime() {
            return reachTime;
        }

        public void setReachTime(String reachTime) {
            this.reachTime = reachTime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


    public static class Invoice implements Serializable{

        private String applyAddressId;
        private String createTime;
        private String id;
        private String invoiceImage;
        private String sort;
        private Uri uri;

        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public String getApplyAddressId() {
            return applyAddressId;
        }

        public void setApplyAddressId(String applyAddressId) {
            this.applyAddressId = applyAddressId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvoiceImage() {
            return invoiceImage;
        }

        public void setInvoiceImage(String invoiceImage) {
            this.invoiceImage = invoiceImage;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }

}
