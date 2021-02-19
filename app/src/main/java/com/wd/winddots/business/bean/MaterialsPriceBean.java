package com.wd.winddots.business.bean;

import java.util.List;

/**
 * FileName: MaterialsPriceBean
 * Author: 郑
 * Date: 2020/8/19 3:22 PM
 * Description:
 */
public class MaterialsPriceBean {

    private int code;

    private List<MaterialsPriceItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MaterialsPriceItem> getData() {
        return data;
    }

    public void setData(List<MaterialsPriceItem> data) {
        this.data = data;
    }

    public static class  MaterialsPriceItem{
        private String priceChange;
        private String priceCreateTime;
        private String priceDate;
        private String priceId;
        private String priceLastTrade;
        private String priceName;
        private String priceType;
        private String priceUnit;

        public String getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(String priceChange) {
            this.priceChange = priceChange;
        }

        public String getPriceCreateTime() {
            return priceCreateTime;
        }

        public void setPriceCreateTime(String priceCreateTime) {
            this.priceCreateTime = priceCreateTime;
        }

        public String getPriceDate() {
            return priceDate;
        }

        public void setPriceDate(String priceDate) {
            this.priceDate = priceDate;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getPriceLastTrade() {
            return priceLastTrade;
        }

        public void setPriceLastTrade(String priceLastTrade) {
            this.priceLastTrade = priceLastTrade;
        }

        public String getPriceName() {
            return priceName;
        }

        public void setPriceName(String priceName) {
            this.priceName = priceName;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }
    }
}
