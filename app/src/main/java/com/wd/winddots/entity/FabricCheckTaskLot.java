package com.wd.winddots.entity;

import java.util.List;

/**
 * FileName: FabricCheckTaskNumber
 * Author: 郑
 * Date: 2021/2/23 11:48 AM
 * Description:
 */
public class FabricCheckTaskLot {

   private String deliveryDate;
   private String recordNumber;
   private String lengthBeforeTotal;
   private String lengthAfterTotal;
   private String weightBeforeTotal;
   private String weightAfterTotal;
   private String machineNumber;
   private String palletNumber;
   private List<FabricCheckTaskRecord> fabricCheckRecords;


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public List<FabricCheckTaskRecord> getFabricCheckRecords() {
        return fabricCheckRecords;
    }

    public void setFabricCheckRecords(List<FabricCheckTaskRecord> fabricCheckRecords) {
        this.fabricCheckRecords = fabricCheckRecords;
    }

    public String getLengthBeforeTotal() {
        return lengthBeforeTotal;
    }

    public void setLengthBeforeTotal(String lengthBeforeTotal) {
        this.lengthBeforeTotal = lengthBeforeTotal;
    }

    public String getLengthAfterTotal() {
        return lengthAfterTotal;
    }

    public void setLengthAfterTotal(String lengthAfterTotal) {
        this.lengthAfterTotal = lengthAfterTotal;
    }

    public String getWeightBeforeTotal() {
        return weightBeforeTotal;
    }

    public void setWeightBeforeTotal(String weightBeforeTotal) {
        this.weightBeforeTotal = weightBeforeTotal;
    }

    public String getWeightAfterTotal() {
        return weightAfterTotal;
    }

    public void setWeightAfterTotal(String weightAfterTotal) {
        this.weightAfterTotal = weightAfterTotal;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getPalletNumber() {
        return palletNumber;
    }

    public void setPalletNumber(String palletNumber) {
        this.palletNumber = palletNumber;
    }
}
