package com.wd.winddots.fast.bean;

import java.util.List;

/**
 * FileName: ClaimingListBean
 * Author: 郑
 * Date: 2020/5/5 10:04 AM
 * Description: 报销列表对象
 */
public class ClaimingListBean {


    private String code;
    private int totalCount;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean{
        private String id;
        private String title;
        private String currency;
        private String amount;
        private String modifyTime;
        private String approvalStatus; //0 未完成   1 已完成  2  驳回
        private String time;


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }



        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        private String voucher;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }


    }

}
