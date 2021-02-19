package com.wd.winddots.business.adapter;

import java.util.List;

/**
 * FileName: BusinessTypeBean
 * Author: 郑
 * Date: 2020/8/24 2:02 PM
 * Description: 原材料和汇率类型对象
 */
public class BusinessTypeBean {

    private int code;
    private List<BusinessTypeItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BusinessTypeItem> getData() {
        return data;
    }

    public void setData(List<BusinessTypeItem> data) {
        this.data = data;
    }

    public class BusinessTypeItem{
        private String priceType;
        private String status;

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
