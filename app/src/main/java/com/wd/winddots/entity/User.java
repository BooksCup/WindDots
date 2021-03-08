package com.wd.winddots.entity;

import java.io.Serializable;

/**
 * 用户
 *
 * @author zhou
 */
public class User implements Serializable {

    private String id;
    private String enterpriseId;
    private String imPassword;
    /**
     * 是否是超级管理员 0:否 1:是
     */
    private Integer isSuperAdmin;
    private String name;
    private String avatar;
    private String departmentName;
    private String jobName;
    private String phone;
    /*
    * 是否仓库管理员
    * */
    private String isFabricCheckAdmin;
    private boolean isDisable = false;
    private boolean select = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public Integer getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Integer isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getIsFabricCheckAdmin() {
        return isFabricCheckAdmin;
    }

    public void setIsFabricCheckAdmin(String isFabricCheckAdmin) {
        this.isFabricCheckAdmin = isFabricCheckAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User that = (User) o;
        // 地址相等
        if (this == that) {
            return true;
        }
        // 如果两个用户ID相等,认为两个用户相等
        if (that.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 17 * result + name.hashCode();
        return result;
    }

}
