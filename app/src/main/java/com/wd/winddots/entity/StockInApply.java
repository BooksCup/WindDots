package com.wd.winddots.entity;

import java.io.Serializable;
import java.util.List;

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

    private String stockGoodsId;
    private String goodsName;
    private String goodsNo;
    private String goodsPhotos;

    private String createUserName;

    private String bizType;
    private String exchangeEnterpriseName;
    private String createTime;
    private String applyStatus;

    private String goodsUnit;
    private String auditUserId;
    private String auditUserName;
    private String copyUserId;
    private String copyUserName;
    private String attrList;
    private String x;
    private String y;

    /**
     * 申请数量
     */
    private String applyNumber;

    /**
     * 备注
     */
    private String remark;

    private String stockImg;

    /**
     * 出入库详细
     */
    private List<StockApplicationInRecord> stockApplicationInRecordList;

    private List<StockApplicationOutRecord> stockApplicationOutRecordList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockGoodsId() {
        return stockGoodsId;
    }

    public void setStockGoodsId(String stockGoodsId) {
        this.stockGoodsId = stockGoodsId;
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

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getCopyUserId() {
        return copyUserId;
    }

    public void setCopyUserId(String copyUserId) {
        this.copyUserId = copyUserId;
    }

    public String getCopyUserName() {
        return copyUserName;
    }

    public void setCopyUserName(String copyUserName) {
        this.copyUserName = copyUserName;
    }

    public String getAttrList() {
        return attrList;
    }

    public void setAttrList(String attrList) {
        this.attrList = attrList;
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

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStockImg() {
        return stockImg;
    }

    public void setStockImg(String stockImg) {
        this.stockImg = stockImg;
    }

    public List<StockApplicationInRecord> getStockApplicationInRecordList() {
        return stockApplicationInRecordList;
    }

    public void setStockApplicationInRecordList(List<StockApplicationInRecord> stockApplicationInRecordList) {
        this.stockApplicationInRecordList = stockApplicationInRecordList;
    }

    public void setStockApplicationOutRecordList(List<StockApplicationOutRecord> stockApplicationOutRecordList) {
        this.stockApplicationOutRecordList = stockApplicationOutRecordList;
    }
}
