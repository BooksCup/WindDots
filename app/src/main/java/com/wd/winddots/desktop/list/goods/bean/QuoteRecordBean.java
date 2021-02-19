package com.wd.winddots.desktop.list.goods.bean;

import java.util.List;

/**
 * FileName: QuoteRecordBean
 * Author: 郑
 * Date: 2020/7/28 3:40 PM
 * Description: 报价记录
 */
public class QuoteRecordBean {
    private int code;
    private int totalCount;
    private List<QuoteRecordItem> data;

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

    public List<QuoteRecordItem> getData() {
        return data;
    }

    public void setData(List<QuoteRecordItem> data) {
        this.data = data;
    }

    public class QuoteRecordItem{
        private String currency;
        private String deleteStatus;
        private String goodsId;
        private String goodsOfferId;
        private String intercourseEnterprise;
        private String offerAgent;
        private String offerPrice;
        private String offerRemark;
        private String offerTime;
        private String offerType;
        private String tradeNo;
        private int type;
        private String unitNo;

        private String quotePrice;
        private String quotePerson;
        private String quoteTime;

        public String getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(String quotePrice) {
            this.quotePrice = quotePrice;
        }

        public String getQuotePerson() {
            return quotePerson;
        }

        public void setQuotePerson(String quotePerson) {
            this.quotePerson = quotePerson;
        }

        public String getQuoteTime() {
            return quoteTime;
        }

        public void setQuoteTime(String quoteTime) {
            this.quoteTime = quoteTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsOfferId() {
            return goodsOfferId;
        }

        public void setGoodsOfferId(String goodsOfferId) {
            this.goodsOfferId = goodsOfferId;
        }

        public String getIntercourseEnterprise() {
            return intercourseEnterprise;
        }

        public void setIntercourseEnterprise(String intercourseEnterprise) {
            this.intercourseEnterprise = intercourseEnterprise;
        }

        public String getOfferAgent() {
            return offerAgent;
        }

        public void setOfferAgent(String offerAgent) {
            this.offerAgent = offerAgent;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getOfferRemark() {
            return offerRemark;
        }

        public void setOfferRemark(String offerRemark) {
            this.offerRemark = offerRemark;
        }

        public String getOfferTime() {
            return offerTime;
        }

        public void setOfferTime(String offerTime) {
            this.offerTime = offerTime;
        }

        public String getOfferType() {
            return offerType;
        }

        public void setOfferType(String offerType) {
            this.offerType = offerType;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUnitNo() {
            return unitNo;
        }

        public void setUnitNo(String unitNo) {
            this.unitNo = unitNo;
        }
    }
}
