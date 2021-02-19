package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.presenter.MineClaimingDetailPresenter;
import com.wd.winddots.fast.presenter.view.MineClaimingDetailView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineClaimingDetailPresenterImpl
 * Author: 郑
 * Date: 2020/5/5 2:09 PM
 * Description:
 */
public class MineClaimingDetailPresenterImpl extends BasePresenter<MineClaimingDetailView> implements MineClaimingDetailPresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public MineClaimingDetailPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }

    @Override
    public void getMineClaimingDetail(String id, String userId) {
        compositeSubscription.add(dataManager.loadApprovalPocessData(id, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getClaimingDetailCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getClaimingDetailError("");
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        ApplyDetailBean bean = gson.fromJson(s, ApplyDetailBean.class);
                        getClaimingDetailSuccess(bean);
                    }
                })
        );
    }
    private void getClaimingDetailSuccess(ApplyDetailBean bean){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.getClaimingDetailSuccess(bean);
        }
    }
    private void getClaimingDetailError(String error){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.getClaimingDetailError(error);
        }
    }
    private void getClaimingDetailCompleted(){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.getClaimingDetailCompleted();
        }

    }



    @Override
    public void recallApply(String id) {
        compositeSubscription.add(dataManager.recallApply(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        recallApplyDetailCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        recallApplyError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            recallApplyError();
                        }else {
                            Map map = Utils.getMapForJson(s);
                            if (map != null && "0".equals(map.get("code"))){
                                recallApplySuccess();
                            }else {
                                recallApplyError();
                            }
                        }
                    }
                })
        );
    }

    private void recallApplySuccess(){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.recallApplySuccess();
        }
    }
    private void recallApplyError(){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.recallApplyError();
        }
    }
    private void recallApplyDetailCompleted(){
        MineClaimingDetailView view = getView();
        if (view != null){
            view.recallApplyDetailCompleted();
        }

    }


}
