package com.wd.winddots.desktop.list.warehouse.bean;

import java.util.List;

/**
 * FileName: WarehouseStockApplicationBean
 * Author: 郑
 * Date: 2020/8/7 9:49 AM
 * Description: 仓库物流
 */
public class WarehouseStockApplicationBean {

    private int code;
    private int totalCount;
    private List<WarehouseStockApplicationItem> data;

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

    public List<WarehouseStockApplicationItem> getData() {
        return data;
    }

    public void setData(List<WarehouseStockApplicationItem> data) {
        this.data = data;
    }

    public class WarehouseStockApplicationItem{

        private String bizType;
        private String checkUserName;
        private double count;
        private String createTime;
        private String goodsId;
        private String goodsName;
        private String goodsNo;
        private String goodsPhotos;
        private String goodsSpecId;
        private String goodsTypeName;
        private String goodsUnit;
        private String id;
        private String price;
        private String receiveAddress;
        private String remark;
        private String residualNumber;
        private String stockApplicationId;
        private String stockType;
        private String wareHouseId;
        private String receiverName;

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        private String typeName;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getCheckUserName() {
            return checkUserName;
        }

        public void setCheckUserName(String checkUserName) {
            this.checkUserName = checkUserName;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public String getGoodsSpecId() {
            return goodsSpecId;
        }

        public void setGoodsSpecId(String goodsSpecId) {
            this.goodsSpecId = goodsSpecId;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getReceiveAddress() {
            return receiveAddress;
        }

        public void setReceiveAddress(String receiveAddress) {
            this.receiveAddress = receiveAddress;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getResidualNumber() {
            return residualNumber;
        }

        public void setResidualNumber(String residualNumber) {
            this.residualNumber = residualNumber;
        }

        public String getStockApplicationId() {
            return stockApplicationId;
        }

        public void setStockApplicationId(String stockApplicationId) {
            this.stockApplicationId = stockApplicationId;
        }

        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }

        public String getWareHouseId() {
            return wareHouseId;
        }

        public void setWareHouseId(String wareHouseId) {
            this.wareHouseId = wareHouseId;
        }
    }



}
