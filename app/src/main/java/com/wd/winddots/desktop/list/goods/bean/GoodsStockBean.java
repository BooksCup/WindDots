package com.wd.winddots.desktop.list.goods.bean;

import java.util.List;

/**
 * FileName: GoodsStockBean
 * Author: 郑
 * Date: 2020/7/29 10:14 AM
 * Description: 物品库存记录
 */
public class GoodsStockBean {

    private int code;
    private StockData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StockData getData() {
        return data;
    }

    public void setData(StockData data) {
        this.data = data;
    }

    public class StockData{
        private Goods goods;
        private List<StockItem> stock;
        private List<StockRecordItem>stockRecord;

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public List<StockItem> getStock() {
            return stock;
        }

        public void setStock(List<StockItem> stock) {
            this.stock = stock;
        }

        public List<StockRecordItem> getStockRecord() {
            return stockRecord;
        }

        public void setStockRecord(List<StockRecordItem> stockRecord) {
            this.stockRecord = stockRecord;
        }
    }



    public static class Goods{
        private String attrList;
        private String auditStatus;
        private String beUsed;
        private String createTime;
        private String deleteStatus;
        private String description;
        private String goodsName;
        private String goodsNo;
        private String goodsPhotos;
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
        private String twoCodeUrl;
        private String userId;
        private String userName;
        private String x;
        private String y;

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

        public String getAttrList() {
            return attrList;
        }

        public void setAttrList(String attrList) {
            this.attrList = attrList;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getBeUsed() {
            return beUsed;
        }

        public void setBeUsed(String beUsed) {
            this.beUsed = beUsed;
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

        public String getTwoCodeUrl() {
            return twoCodeUrl;
        }

        public void setTwoCodeUrl(String twoCodeUrl) {
            this.twoCodeUrl = twoCodeUrl;
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
    }

    public static class StockItem{
        private String goodsId;
        private String goodsSpecId;
        private String residualNumber;
        private String sort;
        private String x;
        private String y;
        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String name5;

        private String bizType;
        private String count;
        private String createTime;
        private String id;
        private String price;
        private String stockApplicationId;
        private String wareHouseId;

        private int type;
        private String viewType;

        public String getViewType() {
            return viewType;
        }

        public void setViewType(String viewType) {
            this.viewType = viewType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStockApplicationId() {
            return stockApplicationId;
        }

        public void setStockApplicationId(String stockApplicationId) {
            this.stockApplicationId = stockApplicationId;
        }

        public String getWareHouseId() {
            return wareHouseId;
        }

        public void setWareHouseId(String wareHouseId) {
            this.wareHouseId = wareHouseId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsSpecId() {
            return goodsSpecId;
        }

        public void setGoodsSpecId(String goodsSpecId) {
            this.goodsSpecId = goodsSpecId;
        }

        public String getResidualNumber() {
            return residualNumber;
        }

        public void setResidualNumber(String residualNumber) {
            this.residualNumber = residualNumber;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getName5() {
            return name5;
        }

        public void setName5(String name5) {
            this.name5 = name5;
        }
    }

    public static class StockRecordItem{
        private String address;
        private String area;
        private String city;
        private String contactName;
        private String contactPhone;
        private String id;
        private String name;
        private String province;
        private String remark;
        private String viewType;
        private List<StockItem> stockApplicationInRecordList;

        public String getViewType() {
            return viewType;
        }

        public void setViewType(String viewType) {
            this.viewType = viewType;
        }

        public List<StockItem> getStockApplicationInRecordList() {
            return stockApplicationInRecordList;
        }

        public void setStockApplicationInRecordList(List<StockItem> stockApplicationInRecordList) {
            this.stockApplicationInRecordList = stockApplicationInRecordList;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
