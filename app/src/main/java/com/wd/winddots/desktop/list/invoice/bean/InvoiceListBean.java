package com.wd.winddots.desktop.list.invoice.bean;

import java.util.List;

/**
 * FileName: QuoteListBean
 * Author: 郑
 * Date: 2020/7/1 11:01 AM
 * Description:
 */
public class InvoiceListBean {

    private int code;
    private int totalCount;
    private List<InvoiceItem> data;

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

    public List<InvoiceItem> getData() {
        return data;
    }

    public void setData(List<InvoiceItem> data) {
        this.data = data;
    }

    public class InvoiceItem{

        private int auditStatus;
        private int inOutStatus;
        private String auditTime;
        private String createTime;
        private String creatorName;
        private String exchangeEnterpriseId;
        private String exchangeEnterpriseName;
        private String id;
        private String invoiceAmountNoTax;
        private String invoiceTaxSumAmount;
        private String shareAmountForOrder;
        private String shareAmountForPayReceive;
        private String shareAmountForReimburse;
        private String taxAmount;

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getInOutStatus() {
            return inOutStatus;
        }

        public void setInOutStatus(int inOutStatus) {
            this.inOutStatus = inOutStatus;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getExchangeEnterpriseId() {
            return exchangeEnterpriseId;
        }

        public void setExchangeEnterpriseId(String exchangeEnterpriseId) {
            this.exchangeEnterpriseId = exchangeEnterpriseId;
        }

        public String getExchangeEnterpriseName() {
            return exchangeEnterpriseName;
        }

        public void setExchangeEnterpriseName(String exchangeEnterpriseName) {
            this.exchangeEnterpriseName = exchangeEnterpriseName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvoiceAmountNoTax() {
            return invoiceAmountNoTax;
        }

        public void setInvoiceAmountNoTax(String invoiceAmountNoTax) {
            this.invoiceAmountNoTax = invoiceAmountNoTax;
        }

        public String getInvoiceTaxSumAmount() {
            return invoiceTaxSumAmount;
        }

        public void setInvoiceTaxSumAmount(String invoiceTaxSumAmount) {
            this.invoiceTaxSumAmount = invoiceTaxSumAmount;
        }

        public String getShareAmountForOrder() {
            return shareAmountForOrder;
        }

        public void setShareAmountForOrder(String shareAmountForOrder) {
            this.shareAmountForOrder = shareAmountForOrder;
        }

        public String getShareAmountForPayReceive() {
            return shareAmountForPayReceive;
        }

        public void setShareAmountForPayReceive(String shareAmountForPayReceive) {
            this.shareAmountForPayReceive = shareAmountForPayReceive;
        }

        public String getShareAmountForReimburse() {
            return shareAmountForReimburse;
        }

        public void setShareAmountForReimburse(String shareAmountForReimburse) {
            this.shareAmountForReimburse = shareAmountForReimburse;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }
    }
}
