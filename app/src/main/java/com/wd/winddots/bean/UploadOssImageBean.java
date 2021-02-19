package com.wd.winddots.bean;

import java.util.List;

/**
 * FileName: UploadOssImageBean
 * Author: 郑
 * Date: 2020/10/14 1:46 PM
 * Description: 上传图片到阿里云对象
 */
public class UploadOssImageBean {

    String code;
    List<String> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
