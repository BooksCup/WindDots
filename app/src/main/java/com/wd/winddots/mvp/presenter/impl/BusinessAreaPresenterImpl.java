package com.wd.winddots.mvp.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.mvp.presenter.BusinessAreaPresenter;
import com.wd.winddots.mvp.view.BusinessareaView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.business.BusinessDataManager;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BusinessAreaPresenterImpl extends BasePresenter<BusinessareaView> implements BusinessAreaPresenter{


    private CompositeSubscription compositeSubscription;
    private BusinessDataManager dataManager;


    public BusinessAreaPresenterImpl() {
        dataManager = new BusinessDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadBusinessAreaData() {
        Log.e("net666","6666666666");
        compositeSubscription.add(dataManager.getBusinessList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        onLoadError("");
                    }

                    @Override
                    public void onNext(String data) {

                        Log.e("net666",data);
                        BusinessBean bean = new Gson().fromJson(data, BusinessBean.class);
                        onSuccess(bean);
                    }
                }));
    }

    public void onSuccess(BusinessBean bean) {
        BusinessareaView view = getView();
        if (view == null) return;
        view.onLoadBusinessAreaDataSuccess(bean);
    }

    public void onLoadError(String msg) {
        BusinessareaView view = getView();
        if (view == null) return;
        view.onLoadBusinessAreaDataError(msg);
    }

    public void onComplete() {
        BusinessareaView view = getView();
        if (view == null) return;
        view.onLoadBusinessAreaDataComplete();
    }
}
