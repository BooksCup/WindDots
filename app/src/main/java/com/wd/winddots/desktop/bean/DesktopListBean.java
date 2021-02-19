package com.wd.winddots.desktop.bean;

import java.util.List;

public class DesktopListBean {

    private String code;
    private List<StoreListBena> list;
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StoreListBena> getList() {
        return list;
    }

    public void setList(List<StoreListBena> list) {
        this.list = list;
    }







    public static class StoreListBena{
        private String id;
        private String applicationPhoto;
        private String name;
        private String remark;
        private String bottomState;
        private String desktopState;
        private String routeAddress;

        private boolean editModel = false;

        public boolean isEditModel() {
            return editModel;
        }

        public void setEditModel(boolean editModel) {
            this.editModel = editModel;
        }

        public String getApplicationType() {
            return applicationType;
        }

        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        private String applicationType;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApplicationPhoto() {
            return applicationPhoto;
        }

        public void setApplicationPhoto(String applicationPhoto) {
            this.applicationPhoto = applicationPhoto;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRouteAddress() {
            return routeAddress;
        }

        public void setRouteAddress(String routeAddress) {
            this.routeAddress = routeAddress;
        }




        public String getBottomState() {
            return bottomState;
        }

        public void setBottomState(String bottomState) {
            this.bottomState = bottomState;
        }

        public String getDesktopState() {
            return desktopState;
        }

        public void setDesktopState(String desktopState) {
            this.desktopState = desktopState;
        }
    }

}
