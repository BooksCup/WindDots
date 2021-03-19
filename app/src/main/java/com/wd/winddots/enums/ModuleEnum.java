package com.wd.winddots.enums;

/**
 * 系统模块
 *
 * @author zhou
 */
public enum ModuleEnum {
    STOCK_IN("STOCK_IN", "入库"),
    ;

    ModuleEnum(String code, String name) {
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
