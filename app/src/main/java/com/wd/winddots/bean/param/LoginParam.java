package com.wd.winddots.bean.param;

public class LoginParam {
    private String phone;
    private String Password;

    public LoginParam(String phone, String password) {
        this.phone = phone;
        Password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
