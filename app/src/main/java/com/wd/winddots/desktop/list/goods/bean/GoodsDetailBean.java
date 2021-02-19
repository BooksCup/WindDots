package com.wd.winddots.desktop.list.goods.bean;

import java.util.List;

/**
 * FileName: GoodsDetailBean
 * Author: 郑
 * Date: 2020/7/28 11:36 AM
 * Description:
 */
public class GoodsDetailBean {

    private String code;
    private GoodsDetailObj obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GoodsDetailObj getObj() {
        return obj;
    }

    public void setObj(GoodsDetailObj obj) {
        this.obj = obj;
    }

    public class GoodsDetailObj{
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
        private List<GoodsSpecItem> goodsSpecList;

        public List<GoodsSpecItem> getGoodsSpecList() {
            return goodsSpecList;
        }

        public void setGoodsSpecList(List<GoodsSpecItem> goodsSpecList) {
            this.goodsSpecList = goodsSpecList;
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


    public class GoodsSpecItem{
        private String beUsed;
        private String createTime;
        private String display;
        private String isImport;
        private String id;
        private String sort;
        private String goodsName;
        private String goodsNo;
        private String useId;
        private String x;
        private String y;

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

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getIsImport() {
            return isImport;
        }

        public void setIsImport(String isImport) {
            this.isImport = isImport;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getUseId() {
            return useId;
        }

        public void setUseId(String useId) {
            this.useId = useId;
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
}
