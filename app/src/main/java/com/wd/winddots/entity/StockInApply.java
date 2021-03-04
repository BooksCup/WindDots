package com.wd.winddots.entity;

import java.io.Serializable;

/**
 * 入库申请
 *
 * @author zhou
 */
public class StockInApply implements Serializable {

    /**
     * 主键
     */
    private String id;

    private String goodsName;
    private String goodsNo;
    private String goodsPhotos;

    private String createUserName;

    private String bizType;
    private String exchangeEnterpriseName;
    private String createTime;

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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getExchangeEnterpriseName() {
        return exchangeEnterpriseName;
    }

    public void setExchangeEnterpriseName(String exchangeEnterpriseName) {
        this.exchangeEnterpriseName = exchangeEnterpriseName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
