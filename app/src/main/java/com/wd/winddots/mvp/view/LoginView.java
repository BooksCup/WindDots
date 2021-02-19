package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.LoginBean;

public interface LoginView {
    void onLoginSuccess(LoginBean loginBean);
    void onLoginFailure(String msg);
    void onLoginError(String errorMsg);
    void onLoginComplete();
}
