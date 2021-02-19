package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.fast.bean.CostBean;
import com.wd.winddots.fast.presenter.MineClaimingAddDetailPresenter;
import com.wd.winddots.fast.presenter.view.MineClaimingAddDetailView;
import com.wd.winddots.net.ElseDataManager;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineClaimingAddDetailPresenterImpl
 * Author: 郑
 * Date: 2020/5/12 2:32 PM
 * Description:
 */
public class MineClaimingAddDetailPresenterImpl extends BasePresenter<MineClaimingAddDetailView> implements MineClaimingAddDetailPresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;


    public MineClaimingAddDetailPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }

    @Override
    public void getCostType(String enterpeiseId) {
        compositeSubscription.add(dataManager.getCostList(enterpeiseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getCostTypsCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getCostTypsError();
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        CostBean bean = gson.fromJson(s, CostBean.class);
                        getCostTypsSuccess(bean);
                    }
                })
        );
    }

    private void getCostTypsSuccess(CostBean bean) {
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.getCostTypsSuccess(bean);
        }
    }

    private void getCostTypsError() {
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.getCostTypsError();
        }
    }

    private void getCostTypsCompleted() {
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.getCostTypsCompleted();
        }
    }


    @Override
    public void upLoadImage(RequestBody body) {
        compositeSubscription.add(dataManager.uploadBigOSSImage(body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        uploadImageCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        uploadImageError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Gson gson = new Gson();
                        UploadImageBean bean = gson.fromJson(s, UploadImageBean.class);
                        if (bean.getList() != null && bean.getList().size() > 0){
                            uploadImageSuccess(bean);
                        }else {
                            uploadImageError();
                        }

                    }
                })
        );
    }


    private void uploadImageSuccess(UploadImageBean bean){
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.uploadImageSuccess(bean);
        }
    }
    private void uploadImageError(){
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.uploadImageError();
        }
    }
    private void uploadImageCompleted(){
        MineClaimingAddDetailView view = getView();
        if (view != null){
            view.uploadImageCompleted();
        }
    }

}
