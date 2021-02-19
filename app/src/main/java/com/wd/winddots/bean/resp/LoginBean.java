package com.wd.winddots.bean.resp;

public class LoginBean {

    /**
     * code : 0
     * msg : 登录成功
     * data : {"address":"南方花园","avatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/cb16dd99ea59407fb3b941e0a62b60dc.jpg","departmentId":"596a13c9e8c44051b0bc3b8109281003","email":"1561937372@qq.com","enterpriseId":"5bd65ecda4174ad3a85e4985d838eba4","enterpriseName":"南京裁缝铺网络科技有限公司","gender":0,"id":"4baddb121f914d0eb608d5c217b5a6f3","imPassword":"b6p7j5","isSuperAdmin":0,"jobStatus":1,"name":"杨璐璐","phone":"17681403278","schemaName":"db2","userStatus":1}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 南方花园
         * avatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/cb16dd99ea59407fb3b941e0a62b60dc.jpg
         * departmentId : 596a13c9e8c44051b0bc3b8109281003
         * email : 1561937372@qq.com
         * enterpriseId : 5bd65ecda4174ad3a85e4985d838eba4
         * enterpriseName : 南京裁缝铺网络科技有限公司
         * gender : 0
         * id : 4baddb121f914d0eb608d5c217b5a6f3
         * imPassword : b6p7j5
         * isSuperAdmin : 0
         * jobStatus : 1
         * name : 杨璐璐
         * phone : 17681403278
         * schemaName : db2
         * userStatus : 1
         */

        private String address;
        private String avatar;
        private String departmentId;
        private String email;
        private String enterpriseId;
        private String enterpriseName;
        private int gender;
        private String id;
        private String imPassword;
        private int isSuperAdmin;
        private int jobStatus;
        private String name;
        private String phone;
        private String schemaName;
        private int userStatus;
        private int userIsSuperAdmin;

        public int getUserIsSuperAdmin() {
            return userIsSuperAdmin;
        }

        public void setUserIsSuperAdmin(int userIsSuperAdmin) {
            this.userIsSuperAdmin = userIsSuperAdmin;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImPassword() {
            return imPassword;
        }

        public void setImPassword(String imPassword) {
            this.imPassword = imPassword;
        }

        public int getIsSuperAdmin() {
            return isSuperAdmin;
        }

        public void setIsSuperAdmin(int isSuperAdmin) {
            this.isSuperAdmin = isSuperAdmin;
        }

        public int getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public void setSchemaName(String schemaName) {
            this.schemaName = schemaName;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "address='" + address + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", departmentId='" + departmentId + '\'' +
                    ", email='" + email + '\'' +
                    ", enterpriseId='" + enterpriseId + '\'' +
                    ", enterpriseName='" + enterpriseName + '\'' +
                    ", gender=" + gender +
                    ", id='" + id + '\'' +
                    ", imPassword='" + imPassword + '\'' +
                    ", isSuperAdmin=" + isSuperAdmin +
                    ", jobStatus=" + jobStatus +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", schemaName='" + schemaName + '\'' +
                    ", userStatus=" + userStatus +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
