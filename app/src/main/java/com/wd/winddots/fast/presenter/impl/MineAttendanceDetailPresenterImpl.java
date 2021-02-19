package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.presenter.MineAttendanceDetailPresenter;
import com.wd.winddots.fast.presenter.view.MineAttendanceDetailView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineAttendanceDetailPresenterImpl
 * Author: 郑
 * Date: 2020/5/29 12:15 PM
 * Description:
 */
public class MineAttendanceDetailPresenterImpl extends BasePresenter<MineAttendanceDetailView> implements MineAttendanceDetailPresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public MineAttendanceDetailPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    @Override
    public void getApplyDetail(String id, String userId) {
        compositeSubscription.add(dataManager.loadApprovalPocessData(id, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getApplyDetailCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getApplyDetailError("");
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        ApplyDetailBean bean = gson.fromJson(s, ApplyDetailBean.class);
                        getApplyDetailSuccess(bean);
                    }
                })
        );
    }

    private void getApplyDetailSuccess(ApplyDetailBean bean){
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.getApplyDetailSuccess(bean);
        }
    }
    private void getApplyDetailError(String error){
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.getApplyDetailError(error);
        }
    }
    private void getApplyDetailCompleted(){
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.getApplyDetailCompleted();
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
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.recallApplySuccess();
        }
    }
    private void recallApplyError(){
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.recallApplyError();
        }
    }
    private void recallApplyDetailCompleted(){
        MineAttendanceDetailView view = getView();
        if (view != null){
            view.recallApplyDetailCompleted();
        }

    }
}
