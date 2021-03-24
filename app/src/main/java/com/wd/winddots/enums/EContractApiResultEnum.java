package com.wd.winddots.enums;

/**
 * 电子合同返回值
 *
 * @author zhou
 */
public enum EContractApiResultEnum {

    SUCCESS(0, "成功"),
    ;

    EContractApiResultEnum(int code, String message) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

}
