package com.wd.winddots.entity;

/**
 * 往来单位
 *
 * @author zhou
 */
public class RelatedCompany {

    private String id;
    private String name;
    private String shortName;
    private String address;
    private String legalPersonName;
    private String estiblishTime;
    private String logo;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getEstiblishTime() {
        return estiblishTime;
    }

    public void setEstiblishTime(String estiblishTime) {
        this.estiblishTime = estiblishTime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
