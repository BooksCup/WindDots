package com.wd.winddots.entity;

/**
 * 面料盘点-缸信息
 *
 * @author zhou
 */
public class FabricCheckLotInfo {

    /**
     * 缸号
     */
    private String lotNo;

    /**
     * 卷数
     */
    private String num;

    /**
     * 数量
     */
    private String length;

    /**
     * 重量
     */
    private String weight;

    private String id;

    private boolean isEdit = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FabricCheckLotInfo() {

    }

    public FabricCheckLotInfo(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
