package com.wd.winddots.business.bean;

import java.util.List;

/**
 * FileName: RateCurrencyBean
 * Author: 郑
 * Date: 2020/8/20 10:04 AM
 * Description: 汇率
 */
public class RateCurrencyBean {
    
    private int code;
    private List<RateCurrencyItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RateCurrencyItem> getData() {
        return data;
    }

    public void setData(List<RateCurrencyItem> data) {
        this.data = data;
    }

    public class RateCurrencyItem{
        private String rateCashBuy;
        private String rateCashSell;
        private String rateCreateTime;
        private String rateCurrencyBuy;
        private String rateCurrencyName;
        private String rateCurrencySell;
        private String rateId;
        private String rateMiddle;
        private String ratePublishDate;
        private String ratePublishTime;


        public String getRateCashBuy() {
            return rateCashBuy;
        }

        public void setRateCashBuy(String rateCashBuy) {
            this.rateCashBuy = rateCashBuy;
        }

        public String getRateCashSell() {
            return rateCashSell;
        }

        public void setRateCashSell(String rateCashSell) {
            this.rateCashSell = rateCashSell;
        }

        public String getRateCreateTime() {
            return rateCreateTime;
        }

        public void setRateCreateTime(String rateCreateTime) {
            this.rateCreateTime = rateCreateTime;
        }

        public String getRateCurrencyBuy() {
            return rateCurrencyBuy;
        }

        public void setRateCurrencyBuy(String rateCurrencyBuy) {
            this.rateCurrencyBuy = rateCurrencyBuy;
        }

        public String getRateCurrencyName() {
            return rateCurrencyName;
        }

        public void setRateCurrencyName(String rateCurrencyName) {
            this.rateCurrencyName = rateCurrencyName;
        }

        public String getRateCurrencySell() {
            return rateCurrencySell;
        }

        public void setRateCurrencySell(String rateCurrencySell) {
            this.rateCurrencySell = rateCurrencySell;
        }

        public String getRateId() {
            return rateId;
        }

        public void setRateId(String rateId) {
            this.rateId = rateId;
        }

        public String getRateMiddle() {
            return rateMiddle;
        }

        public void setRateMiddle(String rateMiddle) {
            this.rateMiddle = rateMiddle;
        }

        public String getRatePublishDate() {
            return ratePublishDate;
        }

        public void setRatePublishDate(String ratePublishDate) {
            this.ratePublishDate = ratePublishDate;
        }

        public String getRatePublishTime() {
            return ratePublishTime;
        }

        public void setRatePublishTime(String ratePublishTime) {
            this.ratePublishTime = ratePublishTime;
        }
    }
    
}
