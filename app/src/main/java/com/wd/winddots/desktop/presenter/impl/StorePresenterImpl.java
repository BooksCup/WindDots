package com.wd.winddots.desktop.presenter.impl;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.bean.DesktopListBean;
import com.wd.winddots.desktop.presenter.StorePresenter;
import com.wd.winddots.desktop.presenter.view.StoreView;
import com.wd.winddots.net.ElseDataManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: StorePresenterImpl
 * Author: 郑
 * Date: 2020/4/29 3:54 PM
 * Description: StorePresenterImpl
 */
public class StorePresenterImpl extends BasePresenter<StoreView> implements StorePresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;



    public StorePresenterImpl(){
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    /*
    * 获取列表
    * */
    @Override
    public void getStoreList(String userId, int pageNum, int numPerPage) {
        compositeSubscription.add(dataManager.getStoreList(userId,pageNum,numPerPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getStoreListCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        getStoreListError();
                    }
                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        DesktopListBean bean = gson.fromJson(s,DesktopListBean.class);
                        if ("0".equals(bean.getCode())){
                            getStoreListSuccess(bean);
                        }else {
                            getStoreListError();
                        }
                    }
                })
                );

    }
    private void getStoreListSuccess(DesktopListBean bean){
        StoreView view = getView();
        if (view != null){
            view.getStoreListSuccess(bean);
        }
    }
    private void getStoreListError(){
        StoreView view = getView();
        if (view != null){
            view.getStoreListError();
        }
    }
    private void getStoreListCompleted(){
        StoreView view = getView();
        if (view != null){
            view.getStoreListCompleted();
        }
    }






}
