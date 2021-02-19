package com.wd.winddots.bean.resp;

import java.util.List;

public class BusinessBean {

    /**
     * title : 加急定制加工 2018 宝宝防蚊裤
     * content : 加急定制加工 2018 宝宝防蚊裤,夏季新款童裤,韩版三条杠冰丝棉儿童防蚊裤,2-6岁,500条,交期为 2019-07-30之前,加工报价10000元
     * relativePhotos : ["http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7a0c64e1a1e24237a87e4ebd31cab305.jpg","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559646117970&di=1cfe873940190f1530a91b9a5573a544&imgtype=0&src=http%3A%2F%2Fimg007.hc360.cn%2Fm4%2FM07%2F3D%2F1D%2FwKhQ6FSdP7yEDiNmAAAAAA5RfME245.jpg"]
     * name : 张经理
     * time : 2019-05-28 15:30
     * targrt : 加急定制 裤子 需求
     * routeName : InterestingDetail
     */


    private int code;
    private List<MaterialsPrice> weavePriceList;
    private ExhangeRate hotExchange;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MaterialsPrice> getWeavePriceList() {
        return weavePriceList;
    }

    public void setWeavePriceList(List<MaterialsPrice> weavePriceList) {
        this.weavePriceList = weavePriceList;
    }

    public ExhangeRate getHotExchange() {
        return hotExchange;
    }

    public void setHotExchange(ExhangeRate hotExchange) {
        this.hotExchange = hotExchange;
    }

    public static class MaterialsPrice{
        private String id;
        private String type;
        private String name;
        private String lastTrade;
        private String unit;
        private String change;
        private String date;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastTrade() {
            return lastTrade;
        }

        public void setLastTrade(String lastTrade) {
            this.lastTrade = lastTrade;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public static class ExhangeRate{
        private String id;
        private String currencyName;
        private String currentPrice;
        private String change;
        private String todayPrice;
        private String yesterdayPrice;
        private String highestPrice;
        private String lowestPrice;
        private String createTime;
        private String titleCss;
        private String yesterdayPriceCss;
        private String currentPriceHtml;
        private String changeHtml;
        private String todayPriceHtml;
        private String yesterdayPriceHtml;
        private String highestPriceHtml;
        private String lowestPriceHtml;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getTodayPrice() {
            return todayPrice;
        }

        public void setTodayPrice(String todayPrice) {
            this.todayPrice = todayPrice;
        }

        public String getYesterdayPrice() {
            return yesterdayPrice;
        }

        public void setYesterdayPrice(String yesterdayPrice) {
            this.yesterdayPrice = yesterdayPrice;
        }

        public String getHighestPrice() {
            return highestPrice;
        }

        public void setHighestPrice(String highestPrice) {
            this.highestPrice = highestPrice;
        }

        public String getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(String lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitleCss() {
            return titleCss;
        }

        public void setTitleCss(String titleCss) {
            this.titleCss = titleCss;
        }

        public String getYesterdayPriceCss() {
            return yesterdayPriceCss;
        }

        public void setYesterdayPriceCss(String yesterdayPriceCss) {
            this.yesterdayPriceCss = yesterdayPriceCss;
        }

        public String getCurrentPriceHtml() {
            return currentPriceHtml;
        }

        public void setCurrentPriceHtml(String currentPriceHtml) {
            this.currentPriceHtml = currentPriceHtml;
        }

        public String getChangeHtml() {
            return changeHtml;
        }

        public void setChangeHtml(String changeHtml) {
            this.changeHtml = changeHtml;
        }

        public String getTodayPriceHtml() {
            return todayPriceHtml;
        }

        public void setTodayPriceHtml(String todayPriceHtml) {
            this.todayPriceHtml = todayPriceHtml;
        }

        public String getYesterdayPriceHtml() {
            return yesterdayPriceHtml;
        }

        public void setYesterdayPriceHtml(String yesterdayPriceHtml) {
            this.yesterdayPriceHtml = yesterdayPriceHtml;
        }

        public String getHighestPriceHtml() {
            return highestPriceHtml;
        }

        public void setHighestPriceHtml(String highestPriceHtml) {
            this.highestPriceHtml = highestPriceHtml;
        }

        public String getLowestPriceHtml() {
            return lowestPriceHtml;
        }

        public void setLowestPriceHtml(String lowestPriceHtml) {
            this.lowestPriceHtml = lowestPriceHtml;
        }
    }

    public class MaterialsTypeItem{
        private String priceType;

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }
    }

    public class BusinessSelectItem{
        private String rateCashBuy;
        private String rateCashSell;
        private String rateCreateTime;
        private String rateCurrencyBuy;
        private String rateCurrencyName;
        private String rateCurrencySell;
        private String rateId;
        private String rateMiddle;
        private String ratePublishDate;
        private String priceChange;
        private String priceCreateTime;
        private String priceDate;
        private String priceId;
        private String priceLastTrade;
        private String priceName;
        private String priceType;
        private String priceUnit;

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



    /*private String title;
    private String content;
    private String name;
    private String time;
    private String targrt;
    private String routeName;
    private List<String> relativePhotos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTargrt() {
        return targrt;
    }

    public void setTargrt(String targrt) {
        this.targrt = targrt;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<String> getRelativePhotos() {
        return relativePhotos;
    }

    public void setRelativePhotos(List<String> relativePhotos) {
        this.relativePhotos = relativePhotos;
    }

    @Override
    public String toString() {
        return "BusinessBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", targrt='" + targrt + '\'' +
                ", routeName='" + routeName + '\'' +
                ", relativePhotos=" + relativePhotos +
                '}';
    }*/
}
