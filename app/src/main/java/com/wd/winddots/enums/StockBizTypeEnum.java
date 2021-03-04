package com.wd.winddots.enums;

/**
 * 出入库类型
 *
 * @author zhou
 */
public enum StockBizTypeEnum {

    STOCK_BIZ_TYPE_PURCHASE_IN("3", "采购入库"),
    ;

    StockBizTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getValue(String type) {
        StockBizTypeEnum[] stockBizTypeEnums = values();
        for (StockBizTypeEnum stockBizTypeEnum : stockBizTypeEnums) {
            if (stockBizTypeEnum.getType().equals(type)) {
                return stockBizTypeEnum.getName();
            }
        }
        return null;
    }

}
