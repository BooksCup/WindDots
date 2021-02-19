package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.bean.ClaimingListBean;
import com.wd.winddots.fast.presenter.MineClaimingPresenter;
import com.wd.winddots.fast.presenter.view.MineClaimingView;
import com.wd.winddots.net.ElseDataManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineClaimingPresenterImpl
 * Author: 郑
 * Date: 2020/5/5 10:42 AM
 * Description:
 */
public class MineClaimingPresenterImpl extends BasePresenter<MineClaimingView> implements MineClaimingPresenter {


    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public MineClaimingPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    @Override
    public void getMineClaimingList(String userId, int pageNum, int numPerPage) {
        compositeSubscription.add(dataManager.getMineClaimingList(userId, pageNum, numPerPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getClaimingListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getClaimingListError("");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Gson gson = new Gson();
                        ClaimingListBean bean = gson.fromJson(s,ClaimingListBean.class);
                        getClaimingListSuccess(bean);

                    }
                })
        );
    }

    /*
    * 获取列表
    * */
    private void getClaimingListSuccess(ClaimingListBean bean) {
        MineClaimingView view = getView();
        if (view != null){
            view.getClaimingListSuccess(bean);
        }
    }
    private void getClaimingListError(String error) {
        MineClaimingView view = getView();
        if (view != null){
            view.getClaimingListError("");
        }
    }
    private void getClaimingListCompleted() {
        MineClaimingView view = getView();
        if (view != null){
            view.getClaimingListCompleted();
        }
    }




}
