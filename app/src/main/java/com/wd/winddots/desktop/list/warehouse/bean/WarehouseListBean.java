package com.wd.winddots.desktop.list.warehouse.bean;

import java.util.List;

/**
 * FileName: WarwhouseListBean
 * Author: 郑
 * Date: 2020/7/31 2:41 PM
 * Description:
 */
public class WarehouseListBean {

    private String code;
    private int totalCount;
    private List<WarehouseListItem> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<WarehouseListItem> getList() {
        return list;
    }

    public void setList(List<WarehouseListItem> list) {
        this.list = list;
    }

    public class WarehouseListItem{
        private String address;
        private String area;
        private String boxCount;
        private String city;
        private String contactName;
        private String contactPhone;
        private String createTime;
        private String createUserId;
        private String id;
        private String name;
        private String province;
        private String remark;
        private String type;
        private String wareHouseChildrenCount;
        private int level;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBoxCount() {
            return boxCount;
        }

        public void setBoxCount(String boxCount) {
            this.boxCount = boxCount;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWareHouseChildrenCount() {
            return wareHouseChildrenCount;
        }

        public void setWareHouseChildrenCount(String wareHouseChildrenCount) {
            this.wareHouseChildrenCount = wareHouseChildrenCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }


}
