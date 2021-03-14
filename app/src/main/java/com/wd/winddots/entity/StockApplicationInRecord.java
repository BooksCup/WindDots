package com.wd.winddots.entity;

import java.io.Serializable;

/**
 */
public class StockApplicationInRecord implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 仓库ID
     */
    private String wareHouseId;

    private String wareHouseName;

    /**
     * 物品ID
     */
    private String goodsId;

    /**
     * 物品规格ID
     */
    private String goodsSpecId;

    /**
     * 出入库数量
     */
    private String count;

    /**
     * 申请数量 目前和count一致
     */
    private String applyNumber;

    /**
     * 出入库ID
     */
    private String stockApplicationId;

    /**
     * 库存数量
     */
    private String residualNumber;

    /**
     * 物品规格的价格
     */
    private String price;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 箱子ID
     */
    private String boxId;

    /**
     * 企业ID
     */
    private String enterpriseId;

    /**
     * 排序
     */
    private int sort;

    /**
     * 币种
     */
    private String currency;

    private String x;
    private String y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getStockApplicationId() {
        return stockApplicationId;
    }

    public void setStockApplicationId(String stockApplicationId) {
        this.stockApplicationId = stockApplicationId;
    }

    public String getResidualNumber() {
        return residualNumber;
    }

    public void setResidualNumber(String residualNumber) {
        this.residualNumber = residualNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
