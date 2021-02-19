package com.wd.winddots.bean.resp;

import java.util.List;

/**
 * FileName: UpgradeBean
 * Author: 郑
 * Date: 2020/6/8 11:27 AM
 * Description:
 */
public class UpgradeBean {



    private List<UpgradeBeanDetail> list;

    public List<UpgradeBeanDetail> getList() {
        return list;
    }

    public void setList(List<UpgradeBeanDetail> list) {
        this.list = list;
    }

    public class UpgradeBeanDetail{
        private int id;
        private int versionCode;
        private String versionName;
        private String apkUrl;
        private String log;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }
}
