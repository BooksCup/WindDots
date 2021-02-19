package com.wd.winddots.desktop.list.order.bean;

import java.util.List;

/**
 * FileName: OrderNumberBean
 * Author: 郑
 * Date: 2020/8/10 12:02 PM
 * Description: 订单数量
 */
public class OrderNumberBean {

    private String code;
    private OrderNumberObj obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrderNumberObj getObj() {
        return obj;
    }

    public void setObj(OrderNumberObj obj) {
        this.obj = obj;
    }

    public class OrderNumberObj{

        private String amount;
        private String applyCount;
        private String createTime;
        private String currency;
        private String deleteStatus;
        private String deliveryCount;
        private String fromEnterpriseId;
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
        private String themeId;
        private String themeTitle;
        private String toEnterpriseId;
        private String toUserId;
        private String type;
        private OrderNumberExt ext;

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

        public String getThemeId() {
            return themeId;
        }

        public void setThemeId(String themeId) {
            this.themeId = themeId;
        }

        public String getThemeTitle() {
            return themeTitle;
        }

        public void setThemeTitle(String themeTitle) {
            this.themeTitle = themeTitle;
        }

        public String getToEnterpriseId() {
            return toEnterpriseId;
        }

        public void setToEnterpriseId(String toEnterpriseId) {
            this.toEnterpriseId = toEnterpriseId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public OrderNumberExt getExt() {
            return ext;
        }

        public void setExt(OrderNumberExt ext) {
            this.ext = ext;
        }
    }

    public class OrderNumberExt{
        private String additionalFees;
        private String additionalFeesJson;
        private String deliveryTimeJson;
        private String display;
        private String goodsJson;
        private String goodsPrice;
        private String id;
        private String pricingType;
        private String skuPrice;
        private String x;
        private String xPrice;
        private String y;
        private String yPrice;


        public String getAdditionalFees() {
            return additionalFees;
        }

        public void setAdditionalFees(String additionalFees) {
            this.additionalFees = additionalFees;
        }

        public String getAdditionalFeesJson() {
            return additionalFeesJson;
        }

        public void setAdditionalFeesJson(String additionalFeesJson) {
            this.additionalFeesJson = additionalFeesJson;
        }

        public String getDeliveryTimeJson() {
            return deliveryTimeJson;
        }

        public void setDeliveryTimeJson(String deliveryTimeJson) {
            this.deliveryTimeJson = deliveryTimeJson;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getGoodsJson() {
            return goodsJson;
        }

        public void setGoodsJson(String goodsJson) {
            this.goodsJson = goodsJson;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPricingType() {
            return pricingType;
        }

        public void setPricingType(String pricingType) {
            this.pricingType = pricingType;
        }

        public String getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(String skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getxPrice() {
            return xPrice;
        }

        public void setxPrice(String xPrice) {
            this.xPrice = xPrice;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getyPrice() {
            return yPrice;
        }

        public void setyPrice(String yPrice) {
            this.yPrice = yPrice;
        }

    }

    public class GoodsObj{
        private String attrList;
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
        private String x;
        private String y;
        private List<GoodsSpecItem> goodsSpecList;

        public String getAttrList() {
            return attrList;
        }

        public void setAttrList(String attrList) {
            this.attrList = attrList;
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

        public List<GoodsSpecItem> getGoodsSpecList() {
            return goodsSpecList;
        }

        public void setGoodsSpecList(List<GoodsSpecItem> goodsSpecList) {
            this.goodsSpecList = goodsSpecList;
        }
    }

    public class GoodsSpecItem{
        private String createTime;
        private String display;
        private String id;
        private String isImport;
        private String sort;
        private String useId;
        private String x;
        private String y;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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


    public static class DeliveryTime{
        private String deliveryCount;
        private String deliveryTime;
        private String id;
        private String masterId;
        private String matchValue;
        private String remark;
        private List<DeliveryTimeDetailItem> deliveryTimeDetailList;

        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String viewType;
        private int type;

        public String getViewType() {
            return viewType;
        }

        public void setViewType(String viewType) {
            this.viewType = viewType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getDeliveryCount() {
            return deliveryCount;
        }

        public void setDeliveryCount(String deliveryCount) {
            this.deliveryCount = deliveryCount;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMasterId() {
            return masterId;
        }

        public void setMasterId(String masterId) {
            this.masterId = masterId;
        }

        public String getMatchValue() {
            return matchValue;
        }

        public void setMatchValue(String matchValue) {
            this.matchValue = matchValue;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<DeliveryTimeDetailItem> getDeliveryTimeDetailList() {
            return deliveryTimeDetailList;
        }

        public void setDeliveryTimeDetailList(List<DeliveryTimeDetailItem> deliveryTimeDetailList) {
            this.deliveryTimeDetailList = deliveryTimeDetailList;
        }
    }


    public static class DeliveryTimeDetailItem{
        private String count;
        private String deliveryTimeId;
        private String goodsSpecId;
        private String id;

        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String viewType;
        private int type;

        public String getViewType() {
            return viewType;
        }

        public void setViewType(String viewType) {
            this.viewType = viewType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDeliveryTimeId() {
            return deliveryTimeId;
        }

        public void setDeliveryTimeId(String deliveryTimeId) {
            this.deliveryTimeId = deliveryTimeId;
        }

        public String getGoodsSpecId() {
            return goodsSpecId;
        }

        public void setGoodsSpecId(String goodsSpecId) {
            this.goodsSpecId = goodsSpecId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
