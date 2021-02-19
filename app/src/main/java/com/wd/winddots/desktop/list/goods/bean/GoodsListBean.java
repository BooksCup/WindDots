package com.wd.winddots.desktop.list.goods.bean;

import java.util.List;

/**
 * FileName: GoodsListBean
 * Author: 郑
 * Date: 2020/7/24 12:05 PM
 * Description:
 */
public class GoodsListBean {

    private String code;
    private int totalCount;
    private List<GoodsListItem> list;

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

    public List<GoodsListItem> getList() {
        return list;
    }

    public void setList(List<GoodsListItem> list) {
        this.list = list;
    }

    public class  GoodsListItem {
        private String attrList;
        private String description;
        private String goodsName;
        private String goodsNo;
        private String goodsPhotos;
        private String goodsTypeId;
        private String goodsTypeName;
        private String goodsUnit;
        private String id;
        private String residualNumber;

        private String photo;
        private String goodsNameNo;
        private String number;
        private String attr1;
        private String attr2;

        public String getResidualNumber() {
            return residualNumber;
        }

        public void setResidualNumber(String residualNumber) {
            this.residualNumber = residualNumber;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getGoodsNameNo() {
            return goodsNameNo;
        }

        public void setGoodsNameNo(String goodsNameNo) {
            this.goodsNameNo = goodsNameNo;
        }

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }

        public String getAttr2() {
            return attr2;
        }

        public void setAttr2(String attr2) {
            this.attr2 = attr2;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getAttrList() {
            return attrList;
        }

        public void setAttrList(String attrList) {
            this.attrList = attrList;
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
    }



}
