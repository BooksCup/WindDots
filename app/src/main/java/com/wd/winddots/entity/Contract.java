package com.wd.winddots.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 合同
 *
 * @author zhou
 */
public class Contract implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 合同类型
     * P：采购
     * S：销售
     */
    private String contractType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 发起方用户ID
     */
    private String fromUserId;

    /**
     * 发起方用户姓名
     */
    private String fromUserName;

    /**
     * 发起方企业ID
     */
    private String fromEnterpriseId;

    /**
     * 发起方企业名称
     */
    private String fromEnterpriseName;

    /**
     * 发起方企业地址
     */
    private String fromEnterpriseAddress;

    /**
     * 接收方用户ID
     */
    private String toUserId;

    /**
     * 接收方用户姓名
     */
    private String toUserName;

    /**
     * 接收方企业ID
     */
    private String toEnterpriseId;

    /**
     * 接收方企业名称
     */
    private String toEnterpriseName;

    /**
     * 接收方企业地址
     */
    private String toEnterpriseAddress;

    /**
     * 合同需求下的主题的组合
     */
    private String themeTitleStr;

    /**
     * 合同需求下的品名的的组合
     */
    private String goodsNamesStr;

    /**
     * 合同需求下的往来物料号的组合
     */
    private String materialNumberStr;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 合同状态
     */
    private String status;

    /**
     * 发送状态
     * 0：未发送
     * 1：已发送
     * 2：已确认
     */
    private String sendStatus;

    /**
     * 合同备注，在附加条款里面，是json类型，有多个备注组合
     */
    private String contractNote;

    /**
     * 合同的相关照片，在附加条款里面
     */
    private String photos;

    /**
     * 合同九宫格照片
     */
    private String goodsPhotos;

    /**
     * 开票状态
     * 00：不开发票
     * 01：普通发票
     * 02：增值发票
     */
    private String invoiceType;

    /**
     * 币种，在附加条款里
     * 1：美元汇率
     * 2：欧元汇率
     * 3：英镑
     * 4：港币
     * 5：加拿大元
     * 6：日元
     * 7：新台币
     * 8：越南币
     * 9：人民币
     */
    private String exchangeRateType;

    /**
     * 合同明细
     */
    private List<ContractDetail> contractDetailList;

    /**
     * 接收提醒
     */
    private String receiveReminding;

    /**
     * 删除状态
     */
    private String deleteStatus;

    /**
     * 来源
     * 0：需求
     * 1：订单
     */
    private String source;

    /**
     * 确认的子记录的主键ID
     */
    private String confirmId;

    /**
     * 确认日期
     */
    private String confirmDate;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 合同预览 是否显示规格 0 不显示 1 显示
     */
    private String isShowSpec;

    /**
     * 标签
     */
    private String tags;

    /**
     * 签署流程创建
     */
    private String flowId;

    /**
     * 签订时间
     */
    private String signDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromEnterpriseId() {
        return fromEnterpriseId;
    }

    public void setFromEnterpriseId(String fromEnterpriseId) {
        this.fromEnterpriseId = fromEnterpriseId;
    }

    public String getFromEnterpriseName() {
        return fromEnterpriseName;
    }

    public void setFromEnterpriseName(String fromEnterpriseName) {
        this.fromEnterpriseName = fromEnterpriseName;
    }

    public String getFromEnterpriseAddress() {
        return fromEnterpriseAddress;
    }

    public void setFromEnterpriseAddress(String fromEnterpriseAddress) {
        this.fromEnterpriseAddress = fromEnterpriseAddress;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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

    public String getToEnterpriseAddress() {
        return toEnterpriseAddress;
    }

    public void setToEnterpriseAddress(String toEnterpriseAddress) {
        this.toEnterpriseAddress = toEnterpriseAddress;
    }

    public String getThemeTitleStr() {
        return themeTitleStr;
    }

    public void setThemeTitleStr(String themeTitleStr) {
        this.themeTitleStr = themeTitleStr;
    }

    public String getGoodsNamesStr() {
        return goodsNamesStr;
    }

    public void setGoodsNamesStr(String goodsNamesStr) {
        this.goodsNamesStr = goodsNamesStr;
    }

    public String getMaterialNumberStr() {
        return materialNumberStr;
    }

    public void setMaterialNumberStr(String materialNumberStr) {
        this.materialNumberStr = materialNumberStr;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getContractNote() {
        return contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getExchangeRateType() {
        return exchangeRateType;
    }

    public void setExchangeRateType(String exchangeRateType) {
        this.exchangeRateType = exchangeRateType;
    }

    public List<ContractDetail> getContractDetailList() {
        return contractDetailList;
    }

    public void setContractDetailList(List<ContractDetail> contractDetailList) {
        this.contractDetailList = contractDetailList;
    }

    public String getReceiveReminding() {
        return receiveReminding;
    }

    public void setReceiveReminding(String receiveReminding) {
        this.receiveReminding = receiveReminding;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsShowSpec() {
        return isShowSpec;
    }

    public void setIsShowSpec(String isShowSpec) {
        this.isShowSpec = isShowSpec;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

}
