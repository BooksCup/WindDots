package com.wd.winddots.fast.bean;

import java.util.List;

/**
 * FileName: RelationEnterpriseBean
 * Author: 郑
 * Date: 2020/5/13 2:12 PM
 * Description:
 */
public class RelationEnterpriseBean {


    private int code;
    private List<Enterprise> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Enterprise> getData() {
        return data;
    }

    public void setData(List<Enterprise> data) {
        this.data = data;
    }


    public class Enterprise{
        private String id;
        private String name;
        private String shortName;
        private String address;
        private String operName;
        private String startDate;
        private boolean isOpen = false;
        private List<Account> exchangeEnterpriseAccountList;
        private int accountPosition;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getAccountPosition() {
            return accountPosition;
        }

        public void setAccountPosition(int accountPosition) {
            this.accountPosition = accountPosition;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
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

        public String getOperName() {
            return operName;
        }

        public void setOperName(String operName) {
            this.operName = operName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public List<Account> getExchangeEnterpriseAccountList() {
            return exchangeEnterpriseAccountList;
        }

        public void setExchangeEnterpriseAccountList(List<Account> exchangeEnterpriseAccountList) {
            this.exchangeEnterpriseAccountList = exchangeEnterpriseAccountList;
        }
    }



    public class Account{
        private String bank;
        private String currency;
        private String id;
        private String name;
        private String number;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
