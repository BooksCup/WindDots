package com.wd.winddots.desktop.list.warehouse.bean;

import java.util.List;

/**
 * FileName: WarehouseStockBean
 * Author: 郑
 * Date: 2020/8/5 9:53 AM
 * Description: 仓库库存
 */
public class WarehouseStockBean {

    private int code;
    private WarehouseData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WarehouseData getData() {
        return data;
    }

    public void setData(WarehouseData data) {
        this.data = data;
    }

    public class WarehouseData{

        private List<WarehouseStockItem> stockMap;

        public List<WarehouseStockItem> getStockMap() {
            return stockMap;
        }

        public void setStockMap(List<WarehouseStockItem> stockMap) {
            this.stockMap = stockMap;
        }
    }

    public class WarehouseStockItem{
        private String goodsId;
        private String goodsName;
        private String address;
        private String goodsNo;
        private String goodsSpecId;
        private String goodsTypeName;
        private String residualNumber;
        private String totalPrice;
        private String goodsPhotos;

        public String getGoodsPhotos() {
            return goodsPhotos;
        }

        public void setGoodsPhotos(String goodsPhotos) {
            this.goodsPhotos = goodsPhotos;
        }

        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
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

        public String getResidualNumber() {
            return residualNumber;
        }

        public void setResidualNumber(String residualNumber) {
            this.residualNumber = residualNumber;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

}
