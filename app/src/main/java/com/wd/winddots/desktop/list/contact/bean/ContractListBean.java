package com.wd.winddots.desktop.list.contact.bean;

import java.util.List;

/**
 * FileName: ConaractListBean
 * Author: 郑
 * Date: 2020/12/15 9:33 AM
 * Description: 合同列表
 */
public class ContractListBean {

    private String code;
    private List<ContractListItem> list;
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ContractListItem> getList() {
        return list;
    }

    public void setList(List<ContractListItem> list) {
        this.list = list;
    }

    public class ContractListItem {
        private String contractAmount;
        private String contractNo;
        private String contractNote;
        private String contractType;
        private String createTime;
        private String deleteStatus;
        private String exchangeRateType;
        private String fromEnterpriseId;
        private String fromUserId;
        private String goodsNamesStr;
        private String id;
        private String invoiceType;
        private String isShowSpec;
        private String materialNumberStr;
        private String memo;
        private String modifyTime;
        private String photos;
        private String sendStatus;
        private String signDate;
        private String source;
        private String status;
        private String tags;
        private String themeTitleStr;
        private String title;
        private String toEnterpriseId;
        private String toEnterpriseName;
        private String toUserId;

        public String getContractAmount() {
            return contractAmount;
        }

        public void setContractAmount(String contractAmount) {
            this.contractAmount = contractAmount;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getContractNote() {
            return contractNote;
        }

        public void setContractNote(String contractNote) {
            this.contractNote = contractNote;
        }

        public String getContractType() {
            return contractType;
        }

        public void setContractType(String contractType) {
            this.contractType = contractType;
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

        public String getExchangeRateType() {
            return exchangeRateType;
        }

        public void setExchangeRateType(String exchangeRateType) {
            this.exchangeRateType = exchangeRateType;
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

        public String getGoodsNamesStr() {
            return goodsNamesStr;
        }

        public void setGoodsNamesStr(String goodsNamesStr) {
            this.goodsNamesStr = goodsNamesStr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getIsShowSpec() {
            return isShowSpec;
        }

        public void setIsShowSpec(String isShowSpec) {
            this.isShowSpec = isShowSpec;
        }

        public String getMaterialNumberStr() {
            return materialNumberStr;
        }

        public void setMaterialNumberStr(String materialNumberStr) {
            this.materialNumberStr = materialNumberStr;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public String getSendStatus() {
            return sendStatus;
        }

        public void setSendStatus(String sendStatus) {
            this.sendStatus = sendStatus;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getThemeTitleStr() {
            return themeTitleStr;
        }

        public void setThemeTitleStr(String themeTitleStr) {
            this.themeTitleStr = themeTitleStr;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }
    }

}
