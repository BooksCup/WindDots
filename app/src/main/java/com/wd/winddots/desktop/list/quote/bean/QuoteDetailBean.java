package com.wd.winddots.desktop.list.quote.bean;

import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;

import java.util.List;

/**
 * FileName: QuoteDetailBean
 * Author: 郑
 * Date: 2020/7/21 10:13 AM
 * Description:
 */
public class QuoteDetailBean {
    private int code;
    private List<QuoteDetailItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<QuoteDetailItem> getData() {
        return data;
    }

    public void setData(List<QuoteDetailItem> data) {
        this.data = data;
    }


    public static class QuoteDetailGroup{
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }



    public class QuoteDetailItem{
        private String createTime;
        private String exchangeRate;
        private String goodsCount;
        private String grossProfit;
        private String grossProfitRate;
        private String id;
        private String materialCost;
        private String modifyTime;
        private String processCost;
        private String processCostJson;
        private String productionCost;
        private String quoteId;
        private String quotedPrice;
        private String risk;
        private String riskCoefficient;
        private String taxRate;
        private String tradeCost;
        private String tradeCostJson;
        private String tradeDetail;
        private String userId;
        private String vatRate;
        private List<QuoteMaterialItem> quoteMaterialList;

        private boolean select;

        public boolean isSelect(){
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        List<ExpandGroupItemEntity<QuoteDetailGroup, QuoteMaterialItem>> dataList;

        public List<ExpandGroupItemEntity<QuoteDetailGroup, QuoteMaterialItem>> getDataList() {
            return dataList;
        }

        public void setDataList(List<ExpandGroupItemEntity<QuoteDetailGroup, QuoteMaterialItem>> dataList) {
            this.dataList = dataList;
        }

        private String title1;
        private String title2;
        private String title3;
        private String title4;
        private String title5;

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getTitle3() {
            return title3;
        }

        public void setTitle3(String title3) {
            this.title3 = title3;
        }

        public String getTitle4() {
            return title4;
        }

        public void setTitle4(String title4) {
            this.title4 = title4;
        }

        public String getTitle5() {
            return title5;
        }

        public void setTitle5(String title5) {
            this.title5 = title5;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getGrossProfit() {
            return grossProfit;
        }

        public void setGrossProfit(String grossProfit) {
            this.grossProfit = grossProfit;
        }

        public String getGrossProfitRate() {
            return grossProfitRate;
        }

        public void setGrossProfitRate(String grossProfitRate) {
            this.grossProfitRate = grossProfitRate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialCost() {
            return materialCost;
        }

        public void setMaterialCost(String materialCost) {
            this.materialCost = materialCost;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getProcessCost() {
            return processCost;
        }

        public void setProcessCost(String processCost) {
            this.processCost = processCost;
        }

        public String getProcessCostJson() {
            return processCostJson;
        }

        public void setProcessCostJson(String processCostJson) {
            this.processCostJson = processCostJson;
        }

        public String getProductionCost() {
            return productionCost;
        }

        public void setProductionCost(String productionCost) {
            this.productionCost = productionCost;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
        }

        public String getQuotedPrice() {
            return quotedPrice;
        }

        public void setQuotedPrice(String quotedPrice) {
            this.quotedPrice = quotedPrice;
        }

        public String getRisk() {
            return risk;
        }

        public void setRisk(String risk) {
            this.risk = risk;
        }

        public String getRiskCoefficient() {
            return riskCoefficient;
        }

        public void setRiskCoefficient(String riskCoefficient) {
            this.riskCoefficient = riskCoefficient;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTradeCost() {
            return tradeCost;
        }

        public void setTradeCost(String tradeCost) {
            this.tradeCost = tradeCost;
        }

        public String getTradeCostJson() {
            return tradeCostJson;
        }

        public void setTradeCostJson(String tradeCostJson) {
            this.tradeCostJson = tradeCostJson;
        }

        public String getTradeDetail() {
            return tradeDetail;
        }

        public void setTradeDetail(String tradeDetail) {
            this.tradeDetail = tradeDetail;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVatRate() {
            return vatRate;
        }

        public void setVatRate(String vatRate) {
            this.vatRate = vatRate;
        }

        public List<QuoteMaterialItem> getQuoteMaterialList() {
            return quoteMaterialList;
        }

        public void setQuoteMaterialList(List<QuoteMaterialItem> quoteMaterialList) {
            this.quoteMaterialList = quoteMaterialList;
        }
    }


    public static class QuoteMaterialItem{
        private String createTime;
        private String currency;
        private String description;
        private String exchangeRate;
        private String goodsId;
        private String goodsJson;
        private String id;
        private String materialLocation;
        private String materialNumber;
        private String materialPrice;
        private String materialUnit;
        private String modifyTime;
        private String pieceYardage;
        private String tax;
        private String wastage;

        private String title;
        private String price;
        private String dataType;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsJson() {
            return goodsJson;
        }

        public void setGoodsJson(String goodsJson) {
            this.goodsJson = goodsJson;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialLocation() {
            return materialLocation;
        }

        public void setMaterialLocation(String materialLocation) {
            this.materialLocation = materialLocation;
        }

        public String getMaterialNumber() {
            return materialNumber;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public String getMaterialPrice() {
            return materialPrice;
        }

        public void setMaterialPrice(String materialPrice) {
            this.materialPrice = materialPrice;
        }

        public String getMaterialUnit() {
            return materialUnit;
        }

        public void setMaterialUnit(String materialUnit) {
            this.materialUnit = materialUnit;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPieceYardage() {
            return pieceYardage;
        }

        public void setPieceYardage(String pieceYardage) {
            this.pieceYardage = pieceYardage;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getWastage() {
            return wastage;
        }

        public void setWastage(String wastage) {
            this.wastage = wastage;
        }
    }
}
