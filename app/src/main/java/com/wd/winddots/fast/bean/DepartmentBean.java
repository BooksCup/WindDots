package com.wd.winddots.fast.bean;

import com.wd.winddots.entity.Department;

import java.util.List;

/**
 * FileName: DepartmentBean
 * Author: 郑
 * Date: 2021/1/21 10:15 AM
 * Description: 部门数据模型
 */
public class DepartmentBean {

    private int code;
    private List<Department> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Department> getData() {
        return data;
    }

    public void setData(List<Department> data) {
        this.data = data;
    }
}
