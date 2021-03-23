package com.wd.winddots.enums;

/**
 * 合同类型
 *
 * @author zhou
 */
public enum ContactTypeEnum {

    PURCHASE("P", "采购合同"),
    SELL("S", "销售合同"),
    PROCESSING("W", "加工合同"),
    ;

    private String code;
    private String name;

    ContactTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
