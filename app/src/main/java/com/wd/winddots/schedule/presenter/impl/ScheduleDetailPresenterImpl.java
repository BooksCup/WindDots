package com.wd.winddots.schedule.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.ScheduleBena;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.schedule.bean.ScheduleDetailBean;
import com.wd.winddots.schedule.presenter.ScheduleDetailPresenter;
import com.wd.winddots.schedule.presenter.view.ScheduleDetailView;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: ScheduleDetailPresenterImpl
 * Author: 郑
 * Date: 2020/6/4 11:52 AM
 * Description:
 */
public class ScheduleDetailPresenterImpl extends BasePresenter<ScheduleDetailView> implements ScheduleDetailPresenter {


    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public ScheduleDetailPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    @Override
    public void getScheduleDetail(String userId, String scheduleId) {
        compositeSubscription.add(dataManager.getUserScheduleDetail(userId,scheduleId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addScheduleCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        addScheduleError();
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net666",s);
                        Gson gson = new Gson();
                        ScheduleDetailBean bena = gson.fromJson(s,ScheduleDetailBean.class);

                        getScheduleDetailSuccess(bena.getData());

                    }
                })
        );
    }



    void getScheduleDetailSuccess(ScheduleBena bena){
        ScheduleDetailView view = getView();
        if (view != null){
            view.getScheduleDetailSuccess(bena);
        }
    }
    void getScheduleDetailError(){
        ScheduleDetailView view = getView();
        if (view != null){
            view.getScheduleDetailError();
        }
    }
    void getScheduleDetailCompleted(){
        ScheduleDetailView view = getView();
        if (view != null){
            view.getScheduleDetailCompleted();
        }
    }


    @Override
    public void addSchedule(RequestBody requestBody) {
        compositeSubscription.add(dataManager.addSchedule(requestBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addScheduleCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        addScheduleError();
                    }

                    @Override
                    public void onNext(String s) {
                        addScheduleSuccess();

                    }
                })
        );
    }

    private void addScheduleSuccess() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.addScheduleSuccess();
        }
    }

    private void addScheduleError() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.addScheduleError();
        }
    }

    private void addScheduleCompleted() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.addScheduleCompleted();
        }
    }


    @Override
    public void updateSchedule(RequestBody requestBody) {
        compositeSubscription.add(dataManager.updateSchedule(requestBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        updateScheduleCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateScheduleError();
                    }

                    @Override
                    public void onNext(String s) {
                        updateScheduleSuccess();
                    }
                })
        );
    }

    private void updateScheduleSuccess() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.updateScheduleSuccess();
        }
    }

    private void updateScheduleError() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.updateScheduleError();
        }
    }

    private void updateScheduleCompleted() {
        ScheduleDetailView view = getView();
        if (view != null){
            view.updateScheduleCompleted();
        }
    }



    @Override
    public void deleteScheduleDetail(String scheduleId, String userId) {
        compositeSubscription.add(dataManager.deleteSchedule(scheduleId,userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        deleteScheduleCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteScheduleError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        deleteScheduleSuccess();
                    }
                })
        );
    }

   private void deleteScheduleSuccess(){
       ScheduleDetailView view = getView();
       if (view != null){
           view.deleteScheduleSuccess();
       }
   }
    private void deleteScheduleError(){
        ScheduleDetailView view = getView();
        if (view != null){
            view.deleteScheduleError();
        }
    }
    private  void deleteScheduleCompleted(){
        ScheduleDetailView view = getView();
        if (view != null){
            view.deleteScheduleCompleted();
        }
    }


}
