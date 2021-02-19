package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.fast.bean.LeaveTypeBean;
import com.wd.winddots.fast.presenter.MineAttendancePresenter;
import com.wd.winddots.fast.presenter.view.MineAttendanceView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.view.selector.SelectBean;

import java.util.List;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineAttendancePresenterImpl
 * Author: 郑
 * Date: 2020/5/28 11:17 AM
 * Description:
 */
public class MineAttendancePresenterImpl extends BasePresenter<MineAttendanceView> implements MineAttendancePresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;


    public MineAttendancePresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    @Override
    public void getLeaveTypeList(String enterpriseId) {
        compositeSubscription.add(dataManager.getLeaveTypeList(enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getLeaveTypeListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getLeaveTypeListError();
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        LeaveTypeBean bean = gson.fromJson(s,LeaveTypeBean.class);
                        getLeaveTypeListSuccess(bean.getData());

                    }
                })
        );
    }

    private void getLeaveTypeListSuccess(List<SelectBean> list) {
        MineAttendanceView view = getView();
        if (view != null){
            view.getLeaveTypeListSuccess(list);
        }
    }

    private void getLeaveTypeListError() {
        MineAttendanceView view = getView();
        if (view != null){
            view.getLeaveTypeListError();
        }
    }

    private void getLeaveTypeListCompleted() {
        MineAttendanceView view = getView();
        if (view != null){
            view.getLeaveTypeListCompleted();
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
                        if (bean.getList() != null && bean.getList().size()>0){
                            uploadImageSuccess(bean);
                        }else {
                            uploadImageError();
                        }
                    }
                })
        );
    }

    private void uploadImageSuccess(UploadImageBean bean){
        MineAttendanceView view = getView();
        if (view != null){
            view.uploadImageSuccess(bean);
        }
    }
    private void uploadImageError(){
        MineAttendanceView view = getView();
        if (view != null){
            view.uploadImageError();
        }
    }
    private void uploadImageCompleted(){
        MineAttendanceView view = getView();
        if (view != null){
            view.uploadImageCompleted();
        }
    }


    @Override
    public void addApply(RequestBody body,String enterpriseId) {
        compositeSubscription.add(dataManager.addApply(body,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addApplyCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        addApplyError();
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            addApplyError();
                        }else if (s.length() == 10){
                            addApplySuccess();
                        }else {
                            addApplyError();
                        }
                    }
                })
        );
    }
    private void addApplySuccess(){
        MineAttendanceView view = getView();
        if (view != null){
            view.addApplySuccess();
        }
    }
    private  void addApplyError(){
        MineAttendanceView view = getView();
        if (view != null){
            view.addApplyError();
        }
    }
    private  void addApplyCompleted(){
        MineAttendanceView view = getView();
        if (view != null){
            view.addApplyCompleted();
        }
    }

    @Override
    public void updateApply(RequestBody body,String enterpriseId) {
        compositeSubscription.add(dataManager.updateApply(body,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        updateApplyCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net", String.valueOf(e));
                        updateApplyError();
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            updateApplyError();
                        }else if (s.length() == 10){
                            updateApplySuccess();
                        }else {
                            updateApplyError();
                        }
                    }
                })
        );
    }
    private void updateApplySuccess(){
        MineAttendanceView view = getView();
        if (view != null){
            view.updateApplySuccess();
        }
    }
    private  void updateApplyError(){
        MineAttendanceView view = getView();
        if (view != null){
            view.updateApplyError();
        }
    }
    private  void updateApplyCompleted(){
        MineAttendanceView view = getView();
        if (view != null){
            view.updateApplyCompleted();
        }
    }

}
