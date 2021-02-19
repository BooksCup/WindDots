package com.wd.winddots.desktop.list.check.bean;

import java.util.List;

/**
 * FileName: CheckGoodsBean
 * Author: 郑
 * Date: 2021/1/29 9:15 AM
 * Description: 盘点物品
 */
public class CheckGoodsBean {


    private int code;
    private int totalCount;
    private String msg;
    private List<CheckGoodsItem> data;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CheckGoodsItem> getData() {
        return data;
    }

    public void setData(List<CheckGoodsItem> data) {
        this.data = data;
    }



    public static class CheckGoodsItem {
        private String productName;
        private String productId;
        private GoodsInfo goods;
        private List<CheckEnterprise> qcWarehouseSupplierIdVos;

        public GoodsInfo getGoods() {
            return goods;
        }

        public void setGoods(GoodsInfo goods) {
            this.goods = goods;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public List<CheckEnterprise> getQcWarehouseSupplierIdVos() {
            return qcWarehouseSupplierIdVos;
        }

        public void setQcWarehouseSupplierIdVos(List<CheckEnterprise> qcWarehouseSupplierIdVos) {
            this.qcWarehouseSupplierIdVos = qcWarehouseSupplierIdVos;
        }
    }

    public static class GoodsInfo{
        private String id;
        private String goodsName;
        private String goodsNhotos;
        private String goodsNo;
        private String attrList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsNhotos() {
            return goodsNhotos;
        }

        public void setGoodsNhotos(String goodsNhotos) {
            this.goodsNhotos = goodsNhotos;
        }

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
        }

        public String getAttrList() {
            return attrList;
        }

        public void setAttrList(String attrList) {
            this.attrList = attrList;
        }
    }





    public static class CheckEnterprise{

        private String supplierName;
        private String supplierId;
        List<CheckGang> fabricQcWarehouseList;

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public List<CheckGang> getFabricQcWarehouseList() {
            return fabricQcWarehouseList;
        }

        public void setFabricQcWarehouseList(List<CheckGang> fabricQcWarehouseList) {
            this.fabricQcWarehouseList = fabricQcWarehouseList;
        }
    }

    public static class CheckGang{
        CheckEnterprise enterprise;

        private String id;
        private String productName;
        private String productId;
        private String supplierName;
        private String supplierId;
        private String cylinderNumber;
        private String oddNumber;
        private String width;
        private String widthUnit;
        private String gramWeight;
        private String totalLength;
        private String totalWeight;
        private String colour;
        private String qcNumber;
        private String lengthUnit;
        private String weightUnit;
        private String createTime;
        private String modifyTime;
        private String remark;
        private String oddTime;


        private String goodsName;
        private String goodsNo;


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

        public CheckEnterprise getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(CheckEnterprise enterprise) {
            this.enterprise = enterprise;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getCylinderNumber() {
            return cylinderNumber;
        }

        public void setCylinderNumber(String cylinderNumber) {
            this.cylinderNumber = cylinderNumber;
        }

        public String getOddNumber() {
            return oddNumber;
        }

        public void setOddNumber(String oddNumber) {
            this.oddNumber = oddNumber;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getWidthUnit() {
            return widthUnit;
        }

        public void setWidthUnit(String widthUnit) {
            this.widthUnit = widthUnit;
        }

        public String getGramWeight() {
            return gramWeight;
        }

        public void setGramWeight(String gramWeight) {
            this.gramWeight = gramWeight;
        }

        public String getTotalLength() {
            return totalLength;
        }

        public void setTotalLength(String totalLength) {
            this.totalLength = totalLength;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getQcNumber() {
            return qcNumber;
        }

        public void setQcNumber(String qcNumber) {
            this.qcNumber = qcNumber;
        }

        public String getLengthUnit() {
            return lengthUnit;
        }

        public void setLengthUnit(String lengthUnit) {
            this.lengthUnit = lengthUnit;
        }

        public String getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(String weightUnit) {
            this.weightUnit = weightUnit;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOddTime() {
            return oddTime;
        }

        public void setOddTime(String oddTime) {
            this.oddTime = oddTime;
        }
    }
}
