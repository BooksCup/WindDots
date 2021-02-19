package com.wd.winddots.mvp.presenter.impl;


import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.LoginBean;
import com.wd.winddots.mvp.view.LoginView;
import com.wd.winddots.mvp.presenter.LoginPresenter;
import com.wd.winddots.net.ElseDataManager;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter{
    public final ElseDataManager dataManager;
    public final CompositeSubscription compositeSubscription;


    public LoginPresenterImpl() {
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void login(RequestBody body) {
        compositeSubscription.add(dataManager.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onLoginComplete();
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onLoginError("error: " + e.getMessage());
                        Log.e("net666", String.valueOf(e));
                        onLoginFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(String loginData) {
                        Log.e("net666",loginData);
                        if (loginData != null){
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(loginData, LoginBean.class);
                            if (loginBean != null){
                                int code = loginBean.getCode();
                                if (code == 0){
                                    onLoginSuccess(loginBean);
                                } else {
                                    onLoginFailure(loginBean.getMsg());
                                }
                            }
                        }
                    }
                })
        );
    }

    public void onLoginSuccess(LoginBean loginBean) {
        LoginView view = getView();
        if (view == null) return;

        view.onLoginSuccess(loginBean);
    }

    public void onLoginFailure( String msg) {
        LoginView view = getView();
        if (view == null) return;

        view.onLoginFailure(msg);
    }

    public void onLoginError( String errorMsg) {
        LoginView view = getView();
        if (view == null) return;

        view.onLoginError(errorMsg);
    }

    public void onLoginComplete() {
        getView().onLoginComplete();
    }
}
