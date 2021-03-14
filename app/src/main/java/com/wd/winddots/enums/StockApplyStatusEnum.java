package com.wd.winddots.enums;

/**
 * 出入库申请状态
 *
 * @author zhou
 */
public enum StockApplyStatusEnum {

    STOCK_APPLY_STATUS_DRAFT("0", "草稿"),
    STOCK_APPLY_STATUS_UNCONFIRMED("1", "未确认"),
    STOCK_APPLY_STATUS_CONFIRMED("2", "已确认"),
    STOCK_APPLY_STATUS_REJECT("3", "驳回"),
    ;

    StockApplyStatusEnum(String status, String value) {
        this.status = status;
        this.value = value;
    }

    private String status;
    private String value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
