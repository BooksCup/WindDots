package com.wd.winddots.bean.resp;

import java.util.List;

public class ApprovalProcessBean {

    private TimeData timeData;
    private MoneyData moneyData;

    public MoneyData getMoneyData() {
        return moneyData;
    }

    public void setMoneyData(MoneyData moneyData) {
        this.moneyData = moneyData;
    }

    public TimeData getTimeData() {
        return timeData;
    }

    public void setTimeData(TimeData timeData) {
        this.timeData = timeData;
    }

    @Override
    public String toString() {
        return "ApprovalProcessBean{" +
                "timeData=" + timeData +
                '}';
    }

    public static class TimeData{

        /**
         * amount : 0
         * applyStatus : 2
         * applyType : 0
         * approvalStatus : 1
         * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad2abfb54844d4fac7720d6981833dc.jpg
         * cause : 年假出去玩1111
         * copyUsers : [{"addApprovaler":"0","approvalStatus":"0","approvalTime":"2020-02-12 11:04:36","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"9d86970bf5e34963abdb358b92aa08e9","jobName":"developing","sort":"1","status":"0","type":"1","userId":SpHelper.getInstance(mContext.getApplicationContext()).getString("id"),"userName":"郑可超"}]
         * createdBy : 059e71da12ee4d3dab97e3510d184972
         * createdTime : 2020-02-12 11:00:46
         * detailList : [{"addresses":[],"amount":"0","costType":"4","createTime":"2020-02-12 11:04:38","endTime":"2020-02-12 18:00","id":"2f8a5d3fa404477791fc6f7077864d99","startTime":"2020-02-12 09:00"}]
         * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
         * id : e7880aad4596426ba50ce82b4f7c8ba0
         * jobName : 服务端开发
         * leaveDays : 1
         * leaveType : 年假
         * modifyTime : 2020-02-12 11:05:30
         * payType :
         * status : 0
         * type : 0
         * userName : 丁二辉
         * users : [{"addApprovaler":"0","approvalStatus":"1","approvalTime":"2020-02-12 11:05:30","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/5ab1bb9cf1e44e279d89c078fde51f63.JPG","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"f9b3b32996714c7b86faccbcf2cc0a78","jobName":"Testing Engeer","sort":"2","status":"0","type":"0","userId":"510c0631527243788fdb74a14e24bac2","userName":"丁琴"},{"addApprovaler":"0","approvalRemark":"不同意","approvalStatus":"2","approvalTime":"2020-02-12 11:03:11","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/5ab1bb9cf1e44e279d89c078fde51f63.JPG","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"906b72895e1548bd87c86e2ad13f54ca","jobName":"Testing Engeer","sort":"2","status":"1","type":"0","userId":"510c0631527243788fdb74a14e24bac2","userName":"丁琴"}]
         * voucher : []
         */

        private String amount;
        private String applyStatus;
        private String applyType;
        private String approvalStatus;
        private String avatar;
        private String cause;
        private String createdBy;
        private String createdTime;
        private String enterpriseId;
        private String id;
        private String jobName;
        private String leaveDays;
        private String leaveType;
        private String modifyTime;
        private String payType;
        private String status;
        private String type;
        private String userName;
        private String voucher;
        private List<CopyUsersBean> copyUsers;
        private List<DetailListBean> detailList;
        private List<UsersBean> users;


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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public List<CopyUsersBean> getCopyUsers() {
            return copyUsers;
        }

        public void setCopyUsers(List<CopyUsersBean> copyUsers) {
            this.copyUsers = copyUsers;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        @Override
        public String toString() {
            return "TimeData{" +
                    "amount='" + amount + '\'' +
                    ", applyStatus='" + applyStatus + '\'' +
                    ", applyType='" + applyType + '\'' +
                    ", approvalStatus='" + approvalStatus + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", cause='" + cause + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", createdTime='" + createdTime + '\'' +
                    ", enterpriseId='" + enterpriseId + '\'' +
                    ", id='" + id + '\'' +
                    ", jobName='" + jobName + '\'' +
                    ", leaveDays='" + leaveDays + '\'' +
                    ", leaveType='" + leaveType + '\'' +
                    ", modifyTime='" + modifyTime + '\'' +
                    ", payType='" + payType + '\'' +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    ", userName='" + userName + '\'' +
                    ", voucher='" + voucher + '\'' +
                    ", copyUsers=" + copyUsers +
                    ", detailList=" + detailList +
                    ", users=" + users +
                    '}';
        }

        public static class CopyUsersBean {
            /**
             * addApprovaler : 0
             * approvalStatus : 0
             * approvalTime : 2020-02-12 11:04:36
             * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg
             * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
             * id : 9d86970bf5e34963abdb358b92aa08e9
             * jobName : developing
             * sort : 1
             * status : 0
             * type : 1
             * userId : a2c3b2f6d32345b2818be757f5adb54f
             * userName : 郑可超
             */

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

            @Override
            public String toString() {
                return "CopyUsersBean{" +
                        "addApprovaler='" + addApprovaler + '\'' +
                        ", approvalStatus='" + approvalStatus + '\'' +
                        ", approvalTime='" + approvalTime + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", enterpriseId='" + enterpriseId + '\'' +
                        ", id='" + id + '\'' +
                        ", jobName='" + jobName + '\'' +
                        ", sort='" + sort + '\'' +
                        ", status='" + status + '\'' +
                        ", type='" + type + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        '}';
            }
        }

        public static class DetailListBean {
            /**
             * addresses : []
             * amount : 0
             * costType : 4
             * createTime : 2020-02-12 11:04:38
             * endTime : 2020-02-12 18:00
             * id : 2f8a5d3fa404477791fc6f7077864d99
             * startTime : 2020-02-12 09:00
             */

            private String amount;
            private String costType;
            private String createTime;
            private String endTime;
            private String id;
            private String startTime;
            private List<?> addresses;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
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

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public List<?> getAddresses() {
                return addresses;
            }

            public void setAddresses(List<?> addresses) {
                this.addresses = addresses;
            }

            @Override
            public String toString() {
                return "DetailListBean{" +
                        "amount='" + amount + '\'' +
                        ", costType='" + costType + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", id='" + id + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", addresses=" + addresses +
                        '}';
            }
        }

        public static class UsersBean {
            /**
             * addApprovaler : 0
             * approvalStatus : 1
             * approvalTime : 2020-02-12 11:05:30
             * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/5ab1bb9cf1e44e279d89c078fde51f63.JPG
             * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
             * id : f9b3b32996714c7b86faccbcf2cc0a78
             * jobName : Testing Engeer
             * sort : 2
             * status : 0
             * type : 0
             * userId : 510c0631527243788fdb74a14e24bac2
             * userName : 丁琴
             * approvalRemark : 不同意
             */

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

            public String getApprovalRemark() {
                return approvalRemark;
            }

            public void setApprovalRemark(String approvalRemark) {
                this.approvalRemark = approvalRemark;
            }

            @Override
            public String toString() {
                return "UsersBean{" +
                        "addApprovaler='" + addApprovaler + '\'' +
                        ", approvalStatus='" + approvalStatus + '\'' +
                        ", approvalTime='" + approvalTime + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", enterpriseId='" + enterpriseId + '\'' +
                        ", id='" + id + '\'' +
                        ", jobName='" + jobName + '\'' +
                        ", sort='" + sort + '\'' +
                        ", status='" + status + '\'' +
                        ", type='" + type + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", approvalRemark='" + approvalRemark + '\'' +
                        '}';
            }
        }
    }




    //        /**
//         * amount : 976
//         * applyStatus : 1
//         * approvalStatus : 0
//         * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad2abfb54844d4fac7720d6981833dc.jpg
//         * copyUsers : [{"addApprovaler":"0","approvalStatus":"0","approvalTime":"2019-12-24 10:18:28","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/0aa519ad1e09406794b1fc3ae88b5f81.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"a559e0c7a682466f9283cf0c44619bbe","jobName":"web前端开发工程师","sort":"2","status":"0","type":"1","userId":"2907ae6f08f9441c9da8fb635697b8e2","userName":"王永刚"}]
//         * createdBy : 059e71da12ee4d3dab97e3510d184972
//         * createdTime : 2019-12-24 10:18:28
//         * currency : 人民币
//         * detailList : [{"addresses":[{"amount":"888","applyDetailId":"08d4c24812dc48ae9588ae823712e31a","createTime":"2019-12-24 10:18:30","id":"9728d93703e74bbcbe8955683ade294d","invoice":"1","invoices":[],"projectTitle":"55","reachTime":"","time":"2019-12-12"},{"amount":"88","applyDetailId":"08d4c24812dc48ae9588ae823712e31a","createTime":"2019-12-24 10:18:31","id":"f41713b72e6a4b8b9e9ff896cf836286","invoice":"1","invoices":[],"projectTitle":"77","reachTime":"","time":"2019-12-28"}],"amount":"976","costName":"餐饮费","costType":"4","createTime":"2019-12-24 10:18:30","id":"08d4c24812dc48ae9588ae823712e31a","remark":""}]
//         * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
//         * id : 2c6d349a67a54c4684398ceda4e0544d
//         * jobName : 服务端开发
//         * modifyTime : 2020-02-11 16:52:05
//         * payType :
//         * remark :
//         * status : 0
//         * title : 666
//         * type : 4
//         * userName : 丁二辉
//         * users : [{"addApprovaler":"0","approvalStatus":"0","approvalTime":"2020-04-07 10:03:00","approvalType":"0","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/d2fcfa5b4b9b422181807f99c30f2474.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"b8ad670e93bf4042869183be1febe42f","jobName":"前端","sort":"0","status":"0","type":"0","userId":"8db7cd9a467b4d12993c21884810f6ae","userName":"尤玉辉"},{"addApprovaler":"1","approvalStatus":"0","approvalTime":"2020-04-07 10:03:00","approvalType":"2","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"317aa16cc4004d5195284fb3c1e0c113","jobName":"developing","sort":"1","status":"0","type":"0","userId":SpHelper.getInstance(mContext.getApplicationContext()).getString("id"),"userName":"郑可超"}]
//         * voucher : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/1a3955c1e8d9403bb482a0c8fb281b95.jpg
//         */
    public static class MoneyData{

        /**
         * accountBank : 南京银行
         * accountName : 南京银行
         * amount : 132
         * applyStatus : 2
         * approvalStatus : 1
         * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad2abfb54844d4fac7720d6981833dc.jpg
         * copyUsers : [{"addApprovaler":"0","approvalStatus":"0","approvalTime":"2020-04-14 14:27:48","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/0aa519ad1e09406794b1fc3ae88b5f81.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"c69b5cfe4779458bbd0e54e820af9782","jobName":"web前端开发工程师","sort":"2","status":"0","type":"1","userId":"2907ae6f08f9441c9da8fb635697b8e2","userName":"王永刚"}]
         * createdBy : 059e71da12ee4d3dab97e3510d184972
         * createdTime : 2019-12-24 10:15:23
         * currency : 人民币
         * detailList : [{"addresses":[{"amount":"55","applyDetailId":"5b84a7a289ca46cea386ee2c9103fccb","createTime":"2019-12-24 10:15:24","id":"24600b0e19a448b4a1b15d0ce97553d4","invoice":"2","invoices":[{"applyAddressId":"24600b0e19a448b4a1b15d0ce97553d4","createTime":"2019-12-24 10:15:24","id":"0db66ac97c8d4082ba0ff5a967c37a3c","invoiceImage":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/b2fba88a9c4e4622884e5f71e96b77f7.jpg"}],"projectTitle":"44","reachTime":"2019-12-21","time":"2019-12-20"},{"amount":"77","applyDetailId":"5b84a7a289ca46cea386ee2c9103fccb","createTime":"2019-12-24 10:15:25","id":"3c5a49a920a6437b890988801632f6ed","invoice":"0","invoices":[{"applyAddressId":"3c5a49a920a6437b890988801632f6ed","createTime":"2019-12-24 10:15:24","id":"94e869e995cd4676b5c16454a4474dd4","invoiceImage":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/70764826c5c8443384986817aa2b8894.jpg"}],"projectTitle":"6","reachTime":"","time":"2020-01-03"}],"amount":"132","costName":"住宿费","costType":"4","createTime":"2019-12-24 10:15:24","id":"5b84a7a289ca46cea386ee2c9103fccb","remark":""}]
         * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
         * id : 3ef5713118c341e4829c0e25f38b7b54
         * jobName : 服务端开发
         * modifyTime : 2020-02-11 16:52:00
         * payType :
         * remark :
         * status : 0
         * title : 33
         * toEnterpriseAccountId : ec92616c2ca545f48d3bbf04d4a3a814
         * toEnterpriseId : 076f8561318846538c21300a81ec77e8
         * toEnterpriseName : 双登集团
         * type : 5
         * userName : 丁二辉
         * users : [{"addApprovaler":"0","approvalStatus":"1","approvalTime":"2020-02-11 16:52:00","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","id":"691919b9acad4e8d89053670c0c93532","jobName":"developing","sort":"1","status":"0","type":"0","userId":SpHelper.getInstance(mContext.getApplicationContext()).getString("id"),"userName":"郑可超"}]
         * voucher : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/bb7c6cee689d4e7b867a2ce0bbda771b.jpg
         */

        private String accountBank;
        private String accountName;
        private String amount;
        private String applyStatus;
        private String approvalStatus;
        private String avatar;
        private String createdBy;
        private String createdTime;
        private String currency;
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
        private String type;
        private String userName;
        private String voucher;
        private List<CopyUsersBean> copyUsers;
        private List<DetailListBean> detailList;
        private List<UsersBean> users;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public List<CopyUsersBean> getCopyUsers() {
            return copyUsers;
        }

        public void setCopyUsers(List<CopyUsersBean> copyUsers) {
            this.copyUsers = copyUsers;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class CopyUsersBean {
            /**
             * addApprovaler : 0
             * approvalStatus : 0
             * approvalTime : 2020-04-14 14:27:48
             * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/0aa519ad1e09406794b1fc3ae88b5f81.jpg
             * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
             * id : c69b5cfe4779458bbd0e54e820af9782
             * jobName : web前端开发工程师
             * sort : 2
             * status : 0
             * type : 1
             * userId : 2907ae6f08f9441c9da8fb635697b8e2
             * userName : 王永刚
             */

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

            @Override
            public String toString() {
                return "CopyUsersBean{" +
                        "addApprovaler='" + addApprovaler + '\'' +
                        ", approvalStatus='" + approvalStatus + '\'' +
                        ", approvalTime='" + approvalTime + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", enterpriseId='" + enterpriseId + '\'' +
                        ", id='" + id + '\'' +
                        ", jobName='" + jobName + '\'' +
                        ", sort='" + sort + '\'' +
                        ", status='" + status + '\'' +
                        ", type='" + type + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        '}';
            }
        }

        public static class DetailListBean {
            /**
             * addresses : [{"amount":"55","applyDetailId":"5b84a7a289ca46cea386ee2c9103fccb","createTime":"2019-12-24 10:15:24","id":"24600b0e19a448b4a1b15d0ce97553d4","invoice":"2","invoices":[{"applyAddressId":"24600b0e19a448b4a1b15d0ce97553d4","createTime":"2019-12-24 10:15:24","id":"0db66ac97c8d4082ba0ff5a967c37a3c","invoiceImage":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/b2fba88a9c4e4622884e5f71e96b77f7.jpg"}],"projectTitle":"44","reachTime":"2019-12-21","time":"2019-12-20"},{"amount":"77","applyDetailId":"5b84a7a289ca46cea386ee2c9103fccb","createTime":"2019-12-24 10:15:25","id":"3c5a49a920a6437b890988801632f6ed","invoice":"0","invoices":[{"applyAddressId":"3c5a49a920a6437b890988801632f6ed","createTime":"2019-12-24 10:15:24","id":"94e869e995cd4676b5c16454a4474dd4","invoiceImage":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/70764826c5c8443384986817aa2b8894.jpg"}],"projectTitle":"6","reachTime":"","time":"2020-01-03"}]
             * amount : 132
             * costName : 住宿费
             * costType : 4
             * createTime : 2019-12-24 10:15:24
             * id : 5b84a7a289ca46cea386ee2c9103fccb
             * remark :
             */

            private String amount;
            private String costName;
            private String costType;
            private String createTime;
            private String id;
            private String remark;
            private List<AddressesBean> addresses;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public List<AddressesBean> getAddresses() {
                return addresses;
            }

            public void setAddresses(List<AddressesBean> addresses) {
                this.addresses = addresses;
            }

            public static class AddressesBean {
                /**
                 * amount : 55
                 * applyDetailId : 5b84a7a289ca46cea386ee2c9103fccb
                 * createTime : 2019-12-24 10:15:24
                 * id : 24600b0e19a448b4a1b15d0ce97553d4
                 * invoice : 2
                 * invoices : [{"applyAddressId":"24600b0e19a448b4a1b15d0ce97553d4","createTime":"2019-12-24 10:15:24","id":"0db66ac97c8d4082ba0ff5a967c37a3c","invoiceImage":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/b2fba88a9c4e4622884e5f71e96b77f7.jpg"}]
                 * projectTitle : 44
                 * reachTime : 2019-12-21
                 * time : 2019-12-20
                 */

                private String amount;
                private String applyDetailId;
                private String createTime;
                private String id;
                private String invoice;
                private String projectTitle;
                private String reachTime;
                private String time;
                private List<InvoicesBean> invoices;

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

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getInvoice() {
                    return invoice;
                }

                public void setInvoice(String invoice) {
                    this.invoice = invoice;
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

                public List<InvoicesBean> getInvoices() {
                    return invoices;
                }

                public void setInvoices(List<InvoicesBean> invoices) {
                    this.invoices = invoices;
                }

                public static class InvoicesBean {
                    /**
                     * applyAddressId : 24600b0e19a448b4a1b15d0ce97553d4
                     * createTime : 2019-12-24 10:15:24
                     * id : 0db66ac97c8d4082ba0ff5a967c37a3c
                     * invoiceImage : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/b2fba88a9c4e4622884e5f71e96b77f7.jpg
                     */

                    private String applyAddressId;
                    private String createTime;
                    private String id;
                    private String invoiceImage;

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

                    @Override
                    public String toString() {
                        return "InvoicesBean{" +
                                "applyAddressId='" + applyAddressId + '\'' +
                                ", createTime='" + createTime + '\'' +
                                ", id='" + id + '\'' +
                                ", invoiceImage='" + invoiceImage + '\'' +
                                '}';
                    }
                }

                @Override
                public String toString() {
                    return "AddressesBean{" +
                            "amount='" + amount + '\'' +
                            ", applyDetailId='" + applyDetailId + '\'' +
                            ", createTime='" + createTime + '\'' +
                            ", id='" + id + '\'' +
                            ", invoice='" + invoice + '\'' +
                            ", projectTitle='" + projectTitle + '\'' +
                            ", reachTime='" + reachTime + '\'' +
                            ", time='" + time + '\'' +
                            ", invoices=" + invoices +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "DetailListBean{" +
                        "amount='" + amount + '\'' +
                        ", costName='" + costName + '\'' +
                        ", costType='" + costType + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", id='" + id + '\'' +
                        ", remark='" + remark + '\'' +
                        ", addresses=" + addresses +
                        '}';
            }
        }

        public static class UsersBean {
            /**
             * addApprovaler : 0
             * approvalStatus : 1
             * approvalTime : 2020-02-11 16:52:00
             * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg
             * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
             * id : 691919b9acad4e8d89053670c0c93532
             * jobName : developing
             * sort : 1
             * status : 0
             * type : 0
             * userId : a2c3b2f6d32345b2818be757f5adb54f
             * userName : 郑可超
             * approvalRemark:""
             */

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

            @Override
            public String toString() {
                return "UsersBean{" +
                        "addApprovaler='" + addApprovaler + '\'' +
                        ", approvalStatus='" + approvalStatus + '\'' +
                        ", approvalTime='" + approvalTime + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", enterpriseId='" + enterpriseId + '\'' +
                        ", id='" + id + '\'' +
                        ", jobName='" + jobName + '\'' +
                        ", sort='" + sort + '\'' +
                        ", status='" + status + '\'' +
                        ", type='" + type + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", approvalRemark='" + approvalRemark + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MoneyData{" +
                    "accountBank='" + accountBank + '\'' +
                    ", accountName='" + accountName + '\'' +
                    ", amount='" + amount + '\'' +
                    ", applyStatus='" + applyStatus + '\'' +
                    ", approvalStatus='" + approvalStatus + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", createdTime='" + createdTime + '\'' +
                    ", currency='" + currency + '\'' +
                    ", enterpriseId='" + enterpriseId + '\'' +
                    ", id='" + id + '\'' +
                    ", jobName='" + jobName + '\'' +
                    ", modifyTime='" + modifyTime + '\'' +
                    ", payType='" + payType + '\'' +
                    ", remark='" + remark + '\'' +
                    ", status='" + status + '\'' +
                    ", title='" + title + '\'' +
                    ", toEnterpriseAccountId='" + toEnterpriseAccountId + '\'' +
                    ", toEnterpriseId='" + toEnterpriseId + '\'' +
                    ", toEnterpriseName='" + toEnterpriseName + '\'' +
                    ", type='" + type + '\'' +
                    ", userName='" + userName + '\'' +
                    ", voucher='" + voucher + '\'' +
                    ", copyUsers=" + copyUsers +
                    ", detailList=" + detailList +
                    ", users=" + users +
                    '}';
        }
    }
}
