package com.wd.winddots.desktop.list.goods.bean;

import com.wd.winddots.desktop.view.filter.FilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: GoodsTypeBean
 * Author: 郑
 * Date: 2020/7/27 2:45 PM
 * Description:
 */
public class GoodsTypeBean {

    private String code;

    private List<GoodsTypeItem> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GoodsTypeItem> getList() {
        return list;
    }

    public void setList(List<GoodsTypeItem> list) {
        this.list = list;
    }

    public static class GoodsTypeItem{
        private String createTime;
        private String enterpriseId;
        private String id;
        private String name;
        private String userId;
        private boolean select = false;
        private List<FilterBean> filterBeanList = new ArrayList<>();

        public List<FilterBean> getFilterBeanList() {
            return filterBeanList;
        }

        public void setFilterBeanList(List<FilterBean> filterBeanList) {
            this.filterBeanList = filterBeanList;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
