package com.wd.winddots.entity;

import java.io.Serializable;

/**
 * 订单
 *
 * @author zhou
 */
public class Order implements Serializable {

    private String goodsName;
    private String goodsNo;
    private String goodsPhotos;
    private String relatedCompanyName;
    private String relatedCompanyShortName;
    private String deliveryDates;
    private String orderNo;
    private String orderTheme;

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

    public String getRelatedCompanyName() {
        return relatedCompanyName;
    }

    public void setRelatedCompanyName(String relatedCompanyName) {
        this.relatedCompanyName = relatedCompanyName;
    }

    public String getRelatedCompanyShortName() {
        return relatedCompanyShortName;
    }

    public void setRelatedCompanyShortName(String relatedCompanyShortName) {
        this.relatedCompanyShortName = relatedCompanyShortName;
    }

    public String getDeliveryDates() {
        return deliveryDates;
    }

    public void setDeliveryDates(String deliveryDates) {
        this.deliveryDates = deliveryDates;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTheme() {
        return orderTheme;
    }

    public void setOrderTheme(String orderTheme) {
        this.orderTheme = orderTheme;
    }

}
