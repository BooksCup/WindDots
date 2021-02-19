package com.wd.winddots.desktop.list.invoice.bean;

import java.util.List;

/**
 * FileName: InvoiceListGroupBean
 * Author: 郑
 * Date: 2020/7/1 12:15 PM
 * Description:
 */
public class InvoiceListGroupBean {

    private int code;
    private int totalCount;
    private List<InvoiceDetail> data;

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

    public List<InvoiceDetail> getData() {
        return data;
    }

    public void setData(List<InvoiceDetail> data) {
        this.data = data;
    }

    public class  InvoiceDetail{
        private int auditStatus;
        private int checkNum;
        private int currency;
        private int inOutStatus;
        private String invoiceTypeCode;
        private int relateStatus;
        private int serviceType;
        private String voidMark;
        private String billingTime;
        private String checkCode;
        private String creatorName;
        private String creatorId;
        private String enterpriseId;
        private String exchangeEnterpriseId;
        private String exchangeEnterpriseName;
        private String id;
        private String invoiceDataCode;
        private String invoiceNumber;
        private String invoiceRemarks;
        private String taxAmount;
        private String auditTime;
        private String createTime;
        private String invoiceTypeName;
        private String isBillMark;
        private String purchaserName;
        private String salesName;
        private String salesTaxpayerAddress;
        private String salesTaxpayerBankAccount;
        private String salesTaxpayerNum;
        private String shareAmountForInvoice;
        private String taxDiskCode;
        private String taxpayerAddressOrId;
        private String taxpayerBankAccount;
        private String taxpayerNumber;
        private String tollSign;
        private String tollSignName;
        private String totalAmount;
        private String totalTaxNum;
        private String totalTaxSum;
        private String goodserviceName;
        private String model;
        private String unit;
        private String numbers;
        private String price;
        private String sums;
        private String taxRate;
        private String tax;
        private List<InvoiceDetailData> invoiceDetailData;

        public List<InvoiceDetailData> getInvoiceDetailData() {
            return invoiceDetailData;
        }

        public void setInvoiceDetailData(List<InvoiceDetailData> invoiceDetailData) {
            this.invoiceDetailData = invoiceDetailData;
        }

        public String getGoodserviceName() {
            return goodserviceName;
        }

        public void setGoodserviceName(String goodserviceName) {
            this.goodserviceName = goodserviceName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSums() {
            return sums;
        }

        public void setSums(String sums) {
            this.sums = sums;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getCheckNum() {
            return checkNum;
        }

        public void setCheckNum(int checkNum) {
            this.checkNum = checkNum;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public int getInOutStatus() {
            return inOutStatus;
        }

        public void setInOutStatus(int inOutStatus) {
            this.inOutStatus = inOutStatus;
        }

        public String getInvoiceTypeCode() {
            return invoiceTypeCode;
        }

        public void setInvoiceTypeCode(String invoiceTypeCode) {
            this.invoiceTypeCode = invoiceTypeCode;
        }

        public int getRelateStatus() {
            return relateStatus;
        }

        public void setRelateStatus(int relateStatus) {
            this.relateStatus = relateStatus;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public String getVoidMark() {
            return voidMark;
        }

        public void setVoidMark(String voidMark) {
            this.voidMark = voidMark;
        }

        public String getBillingTime() {
            return billingTime;
        }

        public void setBillingTime(String billingTime) {
            this.billingTime = billingTime;
        }

        public String getCheckCode() {
            return checkCode;
        }

        public void setCheckCode(String checkCode) {
            this.checkCode = checkCode;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
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

        public String getInvoiceDataCode() {
            return invoiceDataCode;
        }

        public void setInvoiceDataCode(String invoiceDataCode) {
            this.invoiceDataCode = invoiceDataCode;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getInvoiceRemarks() {
            return invoiceRemarks;
        }

        public void setInvoiceRemarks(String invoiceRemarks) {
            this.invoiceRemarks = invoiceRemarks;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
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

        public String getInvoiceTypeName() {
            return invoiceTypeName;
        }

        public void setInvoiceTypeName(String invoiceTypeName) {
            this.invoiceTypeName = invoiceTypeName;
        }

        public String getIsBillMark() {
            return isBillMark;
        }

        public void setIsBillMark(String isBillMark) {
            this.isBillMark = isBillMark;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getSalesName() {
            return salesName;
        }

        public void setSalesName(String salesName) {
            this.salesName = salesName;
        }

        public String getSalesTaxpayerAddress() {
            return salesTaxpayerAddress;
        }

        public void setSalesTaxpayerAddress(String salesTaxpayerAddress) {
            this.salesTaxpayerAddress = salesTaxpayerAddress;
        }

        public String getSalesTaxpayerBankAccount() {
            return salesTaxpayerBankAccount;
        }

        public void setSalesTaxpayerBankAccount(String salesTaxpayerBankAccount) {
            this.salesTaxpayerBankAccount = salesTaxpayerBankAccount;
        }

        public String getSalesTaxpayerNum() {
            return salesTaxpayerNum;
        }

        public void setSalesTaxpayerNum(String salesTaxpayerNum) {
            this.salesTaxpayerNum = salesTaxpayerNum;
        }

        public String getShareAmountForInvoice() {
            return shareAmountForInvoice;
        }

        public void setShareAmountForInvoice(String shareAmountForInvoice) {
            this.shareAmountForInvoice = shareAmountForInvoice;
        }

        public String getTaxDiskCode() {
            return taxDiskCode;
        }

        public void setTaxDiskCode(String taxDiskCode) {
            this.taxDiskCode = taxDiskCode;
        }

        public String getTaxpayerAddressOrId() {
            return taxpayerAddressOrId;
        }

        public void setTaxpayerAddressOrId(String taxpayerAddressOrId) {
            this.taxpayerAddressOrId = taxpayerAddressOrId;
        }

        public String getTaxpayerBankAccount() {
            return taxpayerBankAccount;
        }

        public void setTaxpayerBankAccount(String taxpayerBankAccount) {
            this.taxpayerBankAccount = taxpayerBankAccount;
        }

        public String getTaxpayerNumber() {
            return taxpayerNumber;
        }

        public void setTaxpayerNumber(String taxpayerNumber) {
            this.taxpayerNumber = taxpayerNumber;
        }

        public String getTollSign() {
            return tollSign;
        }

        public void setTollSign(String tollSign) {
            this.tollSign = tollSign;
        }

        public String getTollSignName() {
            return tollSignName;
        }

        public void setTollSignName(String tollSignName) {
            this.tollSignName = tollSignName;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTotalTaxNum() {
            return totalTaxNum;
        }

        public void setTotalTaxNum(String totalTaxNum) {
            this.totalTaxNum = totalTaxNum;
        }

        public String getTotalTaxSum() {
            return totalTaxSum;
        }

        public void setTotalTaxSum(String totalTaxSum) {
            this.totalTaxSum = totalTaxSum;
        }
    }

    public class InvoiceDetailData {
        private String goodserviceName;
        private String number;
        private String numbers;
        private String taxRate;
        private String unit;
        private String price;
        private String zeroTaxRateSign;
        private String zeroTaxRateSignName;
        private String lineNum;
        private String model;
        private String sum;
        private String sums;
        private String tax;
        private String isBillLine;

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getSums() {
            return sums;
        }

        public void setSums(String sums) {
            this.sums = sums;
        }

        public String getGoodserviceName() {
            return goodserviceName;
        }

        public void setGoodserviceName(String goodserviceName) {
            this.goodserviceName = goodserviceName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getZeroTaxRateSign() {
            return zeroTaxRateSign;
        }

        public void setZeroTaxRateSign(String zeroTaxRateSign) {
            this.zeroTaxRateSign = zeroTaxRateSign;
        }

        public String getZeroTaxRateSignName() {
            return zeroTaxRateSignName;
        }

        public void setZeroTaxRateSignName(String zeroTaxRateSignName) {
            this.zeroTaxRateSignName = zeroTaxRateSignName;
        }

        public String getLineNum() {
            return lineNum;
        }

        public void setLineNum(String lineNum) {
            this.lineNum = lineNum;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getIsBillLine() {
            return isBillLine;
        }

        public void setIsBillLine(String isBillLine) {
            this.isBillLine = isBillLine;
        }
    }

}
