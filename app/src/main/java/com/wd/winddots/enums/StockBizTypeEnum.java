package com.wd.winddots.enums;

/**
 * 出入库类型
 *
 * @author zhou
 */
public enum StockBizTypeEnum {

    // in
    STOCK_BIZ_TYPE_PURCHASE_IN("3", "采购入库"),
    STOCK_BIZ_TYPE_ASSETS_IN("7", "固定资产入库"),
    STOCK_BIZ_TYPE_LOW_VALUE_CONSUMPTION_GOODS_IN("8", "低值易耗入库"),
    STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN("9", "办公用品入库"),
    STOCK_BIZ_TYPE_OUTSOURCING_COMPLETION_IN("13", "委外完工入库"),
    STOCK_BIZ_TYPE_TRANSFER_IN("20", "调拨入库"),
    STOCK_BIZ_TYPE_PRODUCTION_COMPLETION_IN("21", "生产完工入库"),
    STOCK_BIZ_TYPE_DUAL_DISTRIBUTION_IN("22", "双经销入库"),
    STOCK_BIZ_TYPE_INVENTORY_PROFIT_IN("23", "盘盈入库"),


    // out
    STOCK_BIZ_TYPE_SELL_OUT("1", "销售出库"),
    STOCK_BIZ_TYPE_PRODUCTION_RECIPIENTS("5", "生产领用"),
    STOCK_BIZ_TYPE_OFFICE_SUPPLIES_OUT(" ", "办公用品领用"),
    STOCK_BIZ_TYPE_TRANSFER_OUT("14", "调拨出库"),
    STOCK_BIZ_TYPE_OUTSOURCING_PICKING("15", "委外领料出库"),
    STOCK_BIZ_TYPE_DUAL_DISTRIBUTION_OUT("16", "双经销出库"),
    STOCK_BIZ_TYPE_LOW_VALUE_CONSUMPTION_GOODS_OUT("17", "低值易耗出库"),
    STOCK_BIZ_TYPE_INVENTORY_LOSS_OUT("18", "盘亏出库"),
    STOCK_BIZ_TYPE_ASSETS_DEPRECIATION("19", "固定资产折旧"),
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
