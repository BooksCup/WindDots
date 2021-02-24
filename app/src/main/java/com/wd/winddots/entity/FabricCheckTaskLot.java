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
}
