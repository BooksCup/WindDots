package com.wd.winddots.desktop.list.enterprise.bean;

import android.util.Log;

import java.util.List;

/**
 * FileName: EnterpriseDetailBean
 * Author: 郑
 * Date: 2020/12/25 10:28 AM
 * Description: 企业详情
 */
public class EnterpriseDetailBean {

    private TycCompany tycCompany;
    private List<CompanyHolderItem> tycCompanyHolderList;
    private List<CompanyChangeInfo> tycCompanyChangeInfoList;
    private List<CompanyLawSuitItem> tycCompanyLawSuitList;


    public TycCompany getTycCompany() {
        return tycCompany;
    }

    public void setTycCompany(TycCompany tycCompany) {
        this.tycCompany = tycCompany;
    }

    public List<CompanyHolderItem> getTycCompanyHolderList() {
        return tycCompanyHolderList;
    }

    public void setTycCompanyHolderList(List<CompanyHolderItem> tycCompanyHolderList) {
        this.tycCompanyHolderList = tycCompanyHolderList;
    }

    public List<CompanyChangeInfo> getTycCompanyChangeInfoList() {
        return tycCompanyChangeInfoList;
    }

    public void setTycCompanyChangeInfoList(List<CompanyChangeInfo> tycCompanyChangeInfoList) {
        this.tycCompanyChangeInfoList = tycCompanyChangeInfoList;
    }

    public List<CompanyLawSuitItem> getTycCompanyLawSuitList() {
        return tycCompanyLawSuitList;
    }

    public void setTycCompanyLawSuitList(List<CompanyLawSuitItem> tycCompanyLawSuitList) {
        this.tycCompanyLawSuitList = tycCompanyLawSuitList;
    }

    public class TycCompany {

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
        private String staffNumRange;
        private long fromTime;
        private String bondName;
        private int isMicroEnt;
        private String usedBondName;
        private int percentileScore;
        private String regInstitute;
        private String regLocation;
        private String industry;
        private long approvedTime;
        private int socialStaffNum;
        private String tags;
        private String taxNumber;
        private String businessScope;
        private String property3;
        private String alias;
        private long updateTimes;
        private String bondType;
        private long toTime;
        private String actualCapital;
        private String companyOrgType;
        private String bondNum;
        private String regCapitalCurrency;
        private String actualCapitalCurrency;
        private String city;
        private String district;
        private String historyNames;

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

        public String getStaffNumRange() {
            return staffNumRange;
        }

        public void setStaffNumRange(String staffNumRange) {
            this.staffNumRange = staffNumRange;
        }

        public long getFromTime() {
            return fromTime;
        }

        public void setFromTime(long fromTime) {
            this.fromTime = fromTime;
        }

        public String getBondName() {
            return bondName;
        }

        public void setBondName(String bondName) {
            this.bondName = bondName;
        }

        public int getIsMicroEnt() {
            return isMicroEnt;
        }

        public void setIsMicroEnt(int isMicroEnt) {
            this.isMicroEnt = isMicroEnt;
        }

        public String getUsedBondName() {
            return usedBondName;
        }

        public void setUsedBondName(String usedBondName) {
            this.usedBondName = usedBondName;
        }

        public int getPercentileScore() {
            return percentileScore;
        }

        public void setPercentileScore(int percentileScore) {
            this.percentileScore = percentileScore;
        }

        public String getRegInstitute() {
            return regInstitute;
        }

        public void setRegInstitute(String regInstitute) {
            this.regInstitute = regInstitute;
        }

        public String getRegLocation() {
            return regLocation;
        }

        public void setRegLocation(String regLocation) {
            this.regLocation = regLocation;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public long getApprovedTime() {
            return approvedTime;
        }

        public void setApprovedTime(long approvedTime) {
            this.approvedTime = approvedTime;
        }

        public int getSocialStaffNum() {
            return socialStaffNum;
        }

        public void setSocialStaffNum(int socialStaffNum) {
            this.socialStaffNum = socialStaffNum;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getProperty3() {
            return property3;
        }

        public void setProperty3(String property3) {
            this.property3 = property3;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public long getUpdateTimes() {
            return updateTimes;
        }

        public void setUpdateTimes(long updateTimes) {
            this.updateTimes = updateTimes;
        }

        public String getBondType() {
            return bondType;
        }

        public void setBondType(String bondType) {
            this.bondType = bondType;
        }

        public long getToTime() {
            return toTime;
        }

        public void setToTime(long toTime) {
            this.toTime = toTime;
        }

        public String getActualCapital() {
            return actualCapital;
        }

        public void setActualCapital(String actualCapital) {
            this.actualCapital = actualCapital;
        }

        public String getCompanyOrgType() {
            return companyOrgType;
        }

        public void setCompanyOrgType(String companyOrgType) {
            this.companyOrgType = companyOrgType;
        }

        public String getBondNum() {
            return bondNum;
        }

        public void setBondNum(String bondNum) {
            this.bondNum = bondNum;
        }

        public String getRegCapitalCurrency() {
            return regCapitalCurrency;
        }

        public void setRegCapitalCurrency(String regCapitalCurrency) {
            this.regCapitalCurrency = regCapitalCurrency;
        }

        public String getActualCapitalCurrency() {
            return actualCapitalCurrency;
        }

        public void setActualCapitalCurrency(String actualCapitalCurrency) {
            this.actualCapitalCurrency = actualCapitalCurrency;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getHistoryNames() {
            return historyNames;
        }

        public void setHistoryNames(String historyNames) {
            this.historyNames = historyNames;
        }
    }


    public class CompanyHolderItem {
        private String holderId;
        private String companyId;
        private String name;
        private int type;
        private String actualCapitalCurrency;
        private String city;
        private String district;
        private String historyNames;
        private List<CapitalItem> capital;
        private List<CapitalItem> capitalActl;

        public String getHolderId() {
            return holderId;
        }

        public void setHolderId(String holderId) {
            this.holderId = holderId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getActualCapitalCurrency() {
            return actualCapitalCurrency;
        }

        public void setActualCapitalCurrency(String actualCapitalCurrency) {
            this.actualCapitalCurrency = actualCapitalCurrency;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getHistoryNames() {
            return historyNames;
        }

        public void setHistoryNames(String historyNames) {
            this.historyNames = historyNames;
        }

        public List<CapitalItem> getCapital() {
            return capital;
        }

        public void setCapital(List<CapitalItem> capital) {
            this.capital = capital;
        }

        public List<CapitalItem> getCapitalActl() {
            return capitalActl;
        }

        public void setCapitalActl(List<CapitalItem> capitalActl) {
            this.capitalActl = capitalActl;
        }
    }

    public class CapitalItem {
        private String amomon;
        private String time;
        private String percent;
        private String paymet;

        public String getAmomon() {
            return amomon;
        }

        public void setAmomon(String amomon) {
            this.amomon = amomon;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getPaymet() {
            return paymet;
        }

        public void setPaymet(String paymet) {
            this.paymet = paymet;
        }
    }


    public static class CompanyChangeInfo {
        private String id;
        private String companyId;
        private String changeTime;
        private String contentAfter;
        private String createTime;
        private String contentBefore;
        private String changeItem;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getChangeTime() {
            return changeTime;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public String getContentAfter() {
            return contentAfter;
        }

        public void setContentAfter(String contentAfter) {
            this.contentAfter = contentAfter;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContentBefore() {
            return contentBefore;
        }

        public void setContentBefore(String contentBefore) {
            this.contentBefore = contentBefore;
        }

        public String getChangeItem() {
            return changeItem;
        }

        public void setChangeItem(String changeItem) {
            this.changeItem = changeItem;
        }
    }

    public class CompanyLawSuitItem {
        private String lawSuitId;
        private String companyId;
        private String plaintiffs;
        private String court;
        private String casereason;
        private String url;
        private String caseno;
        private String id;
        private String title;
        private String submittime;
        private String judgetime;
        private String lawsuitUrl;
        private String casetype;
        private String doctype;
        private String defendants;
//        private String lawSuitId;
//        private String lawSuitId;


        public String getLawSuitId() {
            return lawSuitId;
        }

        public void setLawSuitId(String lawSuitId) {
            this.lawSuitId = lawSuitId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getPlaintiffs() {
            return plaintiffs;
        }

        public void setPlaintiffs(String plaintiffs) {
            this.plaintiffs = plaintiffs;
        }

        public String getCourt() {
            return court;
        }

        public void setCourt(String court) {
            this.court = court;
        }

        public String getCasereason() {
            return casereason;
        }

        public void setCasereason(String casereason) {
            this.casereason = casereason;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCaseno() {
            return caseno;
        }

        public void setCaseno(String caseno) {
            this.caseno = caseno;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubmittime() {
            return submittime;
        }

        public void setSubmittime(String submittime) {
            this.submittime = submittime;
        }

        public String getJudgetime() {
            return judgetime;
        }

        public void setJudgetime(String judgetime) {
            this.judgetime = judgetime;
        }

        public String getLawsuitUrl() {
            return lawsuitUrl;
        }

        public void setLawsuitUrl(String lawsuitUrl) {
            this.lawsuitUrl = lawsuitUrl;
        }

        public String getCasetype() {
            return casetype;
        }

        public void setCasetype(String casetype) {
            this.casetype = casetype;
        }

        public String getDoctype() {
            return doctype;
        }

        public void setDoctype(String doctype) {
            this.doctype = doctype;
        }

        public String getDefendants() {
            return defendants;
        }

        public void setDefendants(String defendants) {
            this.defendants = defendants;
        }
    }


}
