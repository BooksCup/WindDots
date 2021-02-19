package com.wd.winddots.desktop.list.quote.bean;

import java.util.List;
import java.util.Map;

/**
 * FileName: QuoteListBean
 * Author: 郑
 * Date: 2020/7/20 10:24 AM
 * Description:
 */
public class QuoteListBean {

    private String code;
    private int totalCount;
    private List<QuoteListItem> list;

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

    public List<QuoteListItem> getList() {
        return list;
    }

    public void setList(List<QuoteListItem> list) {
        this.list = list;
    }

    public class QuoteListItem{

        private String createTime;
        private String goodsJson;
        private String goodsName;
        private String goodsPhotos;
        private String id;
        private String materialNo;
        private String newestId;
        private String toEnterpriseName;
        private String userName;
        private QuoteListDetail quoteDetail;
        private GoodsInfo quoteGoodsInfo;

        private String quotePrice;
        private String quoteNumber;
        private String quoteTime;
        private String quoteRate;
        private String quoteImage;

        public GoodsInfo getQuoteGoodsInfo() {
            return quoteGoodsInfo;
        }

        public void setQuoteGoodsInfo(GoodsInfo quoteGoodsInfo) {
            this.quoteGoodsInfo = quoteGoodsInfo;
        }

        public String getQuoteImage() {
            return quoteImage;
        }

        public void setQuoteImage(String quoteImage) {
            this.quoteImage = quoteImage;
        }

        public String getQuotePrice() {
            return quotePrice;
        }

        public void setQuotePrice(String quotePrice) {
            this.quotePrice = quotePrice;
        }

        public String getQuoteNumber() {
            return quoteNumber;
        }

        public void setQuoteNumber(String quoteNumber) {
            this.quoteNumber = quoteNumber;
        }

        public String getQuoteTime() {
            return quoteTime;
        }

        public void setQuoteTime(String quoteTime) {
            this.quoteTime = quoteTime;
        }

        public String getQuoteRate() {
            return quoteRate;
        }

        public void setQuoteRate(String quoteRate) {
            this.quoteRate = quoteRate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodsJson() {
            return goodsJson;
        }

        public void setGoodsJson(String goodsJson) {
            this.goodsJson = goodsJson;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsPhotos() {
            return goodsPhotos;
        }

        public void setGoodsPhotos(String goodsPhotos) {
            this.goodsPhotos = goodsPhotos;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialNo() {
            return materialNo;
        }

        public void setMaterialNo(String materialNo) {
            this.materialNo = materialNo;
        }

        public String getNewestId() {
            return newestId;
        }

        public void setNewestId(String newestId) {
            this.newestId = newestId;
        }

        public String getToEnterpriseName() {
            return toEnterpriseName;
        }

        public void setToEnterpriseName(String toEnterpriseName) {
            this.toEnterpriseName = toEnterpriseName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public QuoteListDetail getQuoteDetail() {
            return quoteDetail;
        }

        public void setQuoteDetail(QuoteListDetail quoteDetail) {
            this.quoteDetail = quoteDetail;
        }
    }


    public static class QuoteListDetail{
        private String createTime;
        private String exchangeRate;
        private String goodsCount;
        private String grossProfit;
        private String grossProfitRate;
        private String modifyTime;
        private String productionCost;
        private String quotedPrice;
        private String tradeCost;

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

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getProductionCost() {
            return productionCost;
        }

        public void setProductionCost(String productionCost) {
            this.productionCost = productionCost;
        }

        public String getQuotedPrice() {
            return quotedPrice;
        }

        public void setQuotedPrice(String quotedPrice) {
            this.quotedPrice = quotedPrice;
        }

        public String getTradeCost() {
            return tradeCost;
        }

        public void setTradeCost(String tradeCost) {
            this.tradeCost = tradeCost;
        }
    }

    public static class GoodsInfo {
        private String attrList;
        private String createTime;
        private String deleteStatus;
        private String description;
        private String goodsName;
        private String goodsNo;
        private String goodsPhotos;
        private List<Map> goodsSpecList;
        private String goodsSpecSort;
        private String goodsTypeId;
        private String goodsTypeName;
        private String goodsUnit;
        private String id;
        private String isImport;
        private String modifyTime;
        private String paragraphNo;
        private String residualNumber;
        private String tags;
        private String userId;
        private String x;
        private String y;

        public String getAttrList() {
            return attrList;
        }

        public void setAttrList(String attrList) {
            this.attrList = attrList;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
        }

        public String getGoodsPhotos() {
            return goodsPhotos;
        }

        public void setGoodsPhotos(String goodsPhotos) {
            this.goodsPhotos = goodsPhotos;
        }

        public List<Map> getGoodsSpecList() {
            return goodsSpecList;
        }

        public void setGoodsSpecList(List<Map> goodsSpecList) {
            this.goodsSpecList = goodsSpecList;
        }

        public String getGoodsSpecSort() {
            return goodsSpecSort;
        }

        public void setGoodsSpecSort(String goodsSpecSort) {
            this.goodsSpecSort = goodsSpecSort;
        }

        public String getGoodsTypeId() {
            return goodsTypeId;
        }

        public void setGoodsTypeId(String goodsTypeId) {
            this.goodsTypeId = goodsTypeId;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public String getGoodsUnit() {
            return goodsUnit;
        }

        public void setGoodsUnit(String goodsUnit) {
            this.goodsUnit = goodsUnit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsImport() {
            return isImport;
        }

        public void setIsImport(String isImport) {
            this.isImport = isImport;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getParagraphNo() {
            return paragraphNo;
        }

        public void setParagraphNo(String paragraphNo) {
            this.paragraphNo = paragraphNo;
        }

        public String getResidualNumber() {
            return residualNumber;
        }

        public void setResidualNumber(String residualNumber) {
            this.residualNumber = residualNumber;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }

}
