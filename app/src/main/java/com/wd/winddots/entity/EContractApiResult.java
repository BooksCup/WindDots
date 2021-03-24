package com.wd.winddots.entity;

import java.util.Map;

/**
 * 电子合同API返回值
 *
 * @author zhou
 */
public class EContractApiResult {

    private String message;
    private int code;
    private Map<String, Object> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
