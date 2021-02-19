package com.wd.winddots.register.bean;

/**
 * FileName: CompanyBean
 * Author: 郑
 * Date: 2021/1/13 11:20 AM
 * Description: 公司数据模型
 */
public class CompanyBean {

    private String id;
    private String name;
    private String shortName;
    private String legalPersonName;
    private String estiblishDate;
    private String regLocation;
    private String regStatus;
    private String creditCode;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getEstiblishDate() {
        return estiblishDate;
    }

    public void setEstiblishDate(String estiblishDate) {
        this.estiblishDate = estiblishDate;
    }

    public String getRegLocation() {
        return regLocation;
    }

    public void setRegLocation(String regLocation) {
        this.regLocation = regLocation;
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
}
