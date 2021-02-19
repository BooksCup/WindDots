package com.wd.winddots.desktop.list.order.bean;

import java.util.List;

/**
 * FileName: OrderListBean
 * Author: 郑
 * Date: 2020/8/7 2:08 PM
 * Description: 订单列表
 */
public class OrderListBean {

    private String code;
    private int totalCount;
    private List<OrderListItem> list;

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

    public List<OrderListItem> getList() {
        return list;
    }

    public void setList(List<OrderListItem> list) {
        this.list = list;
    }

    public class OrderListItem{
        private String amount;
        private String applyCount;
        private String createTime;
        private String currency;
        private String deleteStatus;
        private String deliveryCount;
        private String fromEnterpriseId;
        private String fromEnterpriseName;
        private String toEnterpriseId;
        private String toEnterpriseName;
        private String fromUserId;
        private String goodsId;
        private String goodsName;
        private String goodsNo;
        private String goodsPhotos;
        private String goodsTypeId;
        private String goodsTypeName;
        private String id;
        private String integrity;
        private String materialNumber;
        private String modifyTime;
        private String no;
        private String sendStatus;
        private String source;
        private String sourceId;
        private String sourceNo;
        private String tags;
        private String type;
        private String themeTitle;




        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getFromEnterpriseName() {
            return fromEnterpriseName;
        }

        public void setFromEnterpriseName(String fromEnterpriseName) {
            this.fromEnterpriseName = fromEnterpriseName;
        }

        public String getToEnterpriseId() {
            return toEnterpriseId;
        }

        public void setToEnterpriseId(String toEnterpriseId) {
            this.toEnterpriseId = toEnterpriseId;
        }

        public String getToEnterpriseName() {
            return toEnterpriseName;
        }

        public void setToEnterpriseName(String toEnterpriseName) {
            this.toEnterpriseName = toEnterpriseName;
        }

        public String getThemeTitle() {
            return themeTitle;
        }

        public void setThemeTitle(String themeTitle) {
            this.themeTitle = themeTitle;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getDeliveryCount() {
            return deliveryCount;
        }

        public void setDeliveryCount(String deliveryCount) {
            this.deliveryCount = deliveryCount;
        }

        public String getFromEnterpriseId() {
            return fromEnterpriseId;
        }

        public void setFromEnterpriseId(String fromEnterpriseId) {
            this.fromEnterpriseId = fromEnterpriseId;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntegrity() {
            return integrity;
        }

        public void setIntegrity(String integrity) {
            this.integrity = integrity;
        }

        public String getMaterialNumber() {
            return materialNumber;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSendStatus() {
            return sendStatus;
        }

        public void setSendStatus(String sendStatus) {
            this.sendStatus = sendStatus;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getSourceNo() {
            return sourceNo;
        }

        public void setSourceNo(String sourceNo) {
            this.sourceNo = sourceNo;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
