package com.wd.winddots.entity;

import java.io.Serializable;

/**
 * 合同明细
 *
 * @author zhou
 */
public class ContractDetail implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 合同主键ID
     */
    private String contractId;

    /**
     * 来源数据的主键ID
     */
    private String sourceId;

    /**
     * 来源数据的序列化json
     */
    private String sourceJson;

    /**
     * 本次数量总和
     */
    private String thisCount;

    /**
     * 本次数量JSON
     */
    private String thisCountJson;

    /**
     * 排序
     */
    private int sort;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    private String display;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceJson() {
        return sourceJson;
    }

    public void setSourceJson(String sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getThisCount() {
        return thisCount;
    }

    public void setThisCount(String thisCount) {
        this.thisCount = thisCount;
    }

    public String getThisCountJson() {
        return thisCountJson;
    }

    public void setThisCountJson(String thisCountJson) {
        this.thisCountJson = thisCountJson;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
