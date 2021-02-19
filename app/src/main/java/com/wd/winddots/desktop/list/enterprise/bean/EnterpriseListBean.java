package com.wd.winddots.desktop.list.enterprise.bean;

/**
 * FileName: EnterpriseListBean
 * Author: 郑
 * Date: 2020/12/22 11:05 AM
 * Description:
 */
public class EnterpriseListBean {


    public static class EnterpriseItem{
        private String companyId;
        private String regNumber;
        private String regStatus;
        private String creditCode;
        private long estiblishTime;
        private String regCapital;
        private String companyType;
        private String name;
        private String id;
        private String orgNumber;
        private int type;
        private String base;
        private String legalPersonName;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }

        public String getRegStatus() {
            return regStatus;
        }

        public void setRegStatus(String regStatus) {
            this.regStatus = regStatus;
        }

        public String getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(String creditCode) {
            this.creditCode = creditCode;
        }

        public long getEstiblishTime() {
            return estiblishTime;
        }

        public void setEstiblishTime(long estiblishTime) {
            this.estiblishTime = estiblishTime;
        }

        public String getRegCapital() {
            return regCapital;
        }

        public void setRegCapital(String regCapital) {
            this.regCapital = regCapital;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getLegalPersonName() {
            return legalPersonName;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }
    }
}
