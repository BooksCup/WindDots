package com.wd.winddots.enums;

/**
 * 推送类型
 *
 * @author zhou
 */
public enum PushServiceTypeEnum {
    ADD_STOCK_IN_APPLY_OFFICE_SUPPLIES("9", "办公用品入库单"),
    ;

    PushServiceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

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
