package com.wd.winddots.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 物品
 */
public class Goods implements Serializable {

    private String id;

    /**
     * 品名
     */
    private String goodsName;

    /**
     * 图片
     */
    private String goodsPhotos;

    /**
     * 物料号
     */
    private String goodsNo;

    /**
     * 物品属性集合LIST的JSON格式
     */
    private String attrList;
    private String stockNum;
    private String goodsUnit;

    /**
     * 规格x
     */
    private String x;

    /**
     * 规格y
     */
    private String y;

    private List<GoodsSpec> goodsSpecList;

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

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
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

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
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

    public List<GoodsSpec> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }
}
