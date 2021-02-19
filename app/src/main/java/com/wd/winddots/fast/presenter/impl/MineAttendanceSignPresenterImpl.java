package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.bean.LeaveTypeBean;
import com.wd.winddots.fast.bean.MineAttendanceSignBean;
import com.wd.winddots.fast.presenter.MineAttendanceSignPresenter;
import com.wd.winddots.fast.presenter.view.MineAttendanceSignView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineAttendanceSignPresenterImpl
 * Author: 郑
 * Date: 2020/6/2 9:45 AM
 * Description:
 */
public class MineAttendanceSignPresenterImpl extends BasePresenter<MineAttendanceSignView> implements MineAttendanceSignPresenter {
    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;


    public MineAttendanceSignPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }

    @Override
    public void getAttendRecord(String userId) {
        compositeSubscription.add(dataManager.getAttendRecord(userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getAttendRecordCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttendRecordError();
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        MineAttendanceSignBean bean = gson.fromJson(s, MineAttendanceSignBean.class);
                        getAttendRecordSuccess(bean);
                    }
                })
        );
    }




    private void getAttendRecordSuccess(MineAttendanceSignBean bean){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.getAttendRecordSuccess(bean);
        }
    }
    private void getAttendRecordError(){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.getAttendRecordError();
        }
    }
    private void getAttendRecordCompleted(){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.getAttendRecordCompleted();
        }
    }


    @Override
    public void addAttendRecord(String recordUserId,
                                String recordType,
                                String recordRemark,
                                String wifiName,
                                String address,
                                double recordLat,
                                double recordLng) {
        compositeSubscription.add(dataManager.addAttendRecord(recordUserId,recordType,recordRemark,wifiName,address,recordLat,recordLng).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addAttendRecordCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        addAttendRecordError();
                        Log.e("net666", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net666",s);
                        Map data = Utils.getMapForJson(s);
                        if (data == null){
                            addAttendRecordError();
                        }else {
                            String code = (String) data.get("code");
                            if ("0".equals(code)){
                                addAttendRecordSuccess();
                            }else {
                                addAttendRecordError();
                            }
                        }
                    }
                })
        );
    }

    private void addAttendRecordSuccess(){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.addAttendRecordSuccess();
        }
    }
    private void addAttendRecordError(){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.addAttendRecordError();
        }
    }
    private void addAttendRecordCompleted(){
        MineAttendanceSignView view = getView();
        if (view != null){
            view.addAttendRecordCompleted();
        }
    }



}
