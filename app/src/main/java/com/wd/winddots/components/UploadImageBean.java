package com.wd.winddots.components;

import java.util.List;

/**
 * FileName: UploadImageBean
 * Author: 郑
 * Date: 2020/5/12 2:26 PM
 * Description: 上传图片返回结果对象
 */
public class UploadImageBean {

    private String code;
    private String data;
    private List<String> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
