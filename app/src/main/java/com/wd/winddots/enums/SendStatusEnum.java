package com.wd.winddots.enums;

/**
 * 发送状态
 *
 * @author zhou
 */
public enum SendStatusEnum {

    UN_SENT("0", "未发送"),
    SENTED("1", "已发送"),
    CONFIRMED("2", "已确认");

    private String code;
    private String name;

    SendStatusEnum(String code, String name) {
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
