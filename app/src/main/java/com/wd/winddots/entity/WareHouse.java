package com.wd.winddots.entity;

/**
 * 仓库
 *
 * @author zhou
 */
public class WareHouse {

    private String id;
    private String name;
    private String contactName;
    private String contactPhone;
    private String address;
    private String isOwn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(String isOwn) {
        this.isOwn = isOwn;
    }
}
