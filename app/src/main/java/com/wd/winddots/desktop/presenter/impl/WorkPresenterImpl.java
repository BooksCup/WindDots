package com.wd.winddots.desktop.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.bean.DesktopListBean;
import com.wd.winddots.desktop.presenter.WorkPresenter;
import com.wd.winddots.desktop.presenter.view.WorkView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WorkPresenterImpl extends BasePresenter<WorkView> implements WorkPresenter {


    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;



    public WorkPresenterImpl(){
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }


    /*
    * 获取列表数据
    * */
    @Override
    public void getWorkList(String userId) {
        compositeSubscription.add(dataManager.getDesktopList(userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getWorkListCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        getWorkListError(e);
                    }
                    @Override
                    public void onNext(String s) {
                        Log.e("net666getWorkList",s);
                        Gson gson = new Gson();
                        DesktopListBean bean = gson.fromJson(s,DesktopListBean.class);
                        getWorkListSuccess(bean);
                    }
                })
        );
    }
    private void getWorkListSuccess(DesktopListBean listBean) {
        WorkView view = getView();
        if (view != null){
            view.getWorkListSuccess(listBean);
        }
    }
    private void getWorkListError(Throwable e) {
        WorkView view = getView();
        if (view != null){
            view.getWorkListError(e);
        }
    }
    private void getWorkListCompleted() {
        WorkView view = getView();
        if (view != null){
            view.getWorkListCompleted();
        }
    }



    /*
    * 删除办公 item
    * */
    @Override
    public void deleteItem(String userId, String applicationId, List<Map> applicationIds, String enterpriseId) {

        Map body = new HashMap();
        body.put("userId",userId);
        body.put("applicationId",applicationId);
        body.put("applicationType","1");
        body.put("applicationIds",applicationIds);

        Gson gson = new Gson();
        String bodyString = gson.toJson(body);
        Log.e("net666",bodyString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);



        compositeSubscription.add(dataManager.addOrRemoveStore(requestBody,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        deleteItemCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        deleteItemError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Map res = Utils.getMapForJson(s);
                        if (res == null){
                            deleteItemError();
                            return;
                        }
                        int code = (int)res.get("code");
                        if (code == 0){
                            deleteItemSuccess();
                        }else {
                            deleteItemError();
                        }
                    }
                }));

    }

    private void deleteItemSuccess(){
        WorkView view = getView();
        if (view != null){
            view.deleteItemSuccess();
        }
    }
    private void deleteItemError(){
        WorkView view = getView();
        if (view != null){
            view.deleteItemError("s");
        }
    }
    private void deleteItemCompleted(){
        WorkView view = getView();
        if (view != null){
            view.deleteItemCompleted();
        }
    }

    @Override
    public void setApplicationSort(String userId, RequestBody body, String enterpriseId) {
        compositeSubscription.add(dataManager.setApplicationSort(userId,body,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        deleteItemCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        deleteItemError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666ApplicationSort",s);
//                        Map res = Utils.getMapForJson(s);
//                        if (res == null){
//                            deleteItemError();
//                            return;
//                        }
//                        int code = (int)res.get("code");
//                        if (code == 0){
//                            deleteItemSuccess();
//                        }else {
//                            deleteItemError();
//                        }
                    }
                }));

    }
}
