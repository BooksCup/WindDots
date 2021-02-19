package com.wd.winddots.message.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.message.presenter.DiscussChatAddPresenter;
import com.wd.winddots.message.presenter.view.DiscussChatAddView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: DiscussChatAddPresenterImpl
 * Author: 郑
 * Date: 2020/6/9 11:51 AM
 * Description:
 */
public class DiscussChatAddPresenterImpl extends BasePresenter<DiscussChatAddView> implements DiscussChatAddPresenter {

    public final ElseDataManager dataManager;
    public final MsgDataManager msgDataManager;
    public final CompositeSubscription compositeSubscription;

    public DiscussChatAddPresenterImpl() {
        dataManager = new ElseDataManager();
        msgDataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void addDiscuss(RequestBody body) {
        compositeSubscription.add(msgDataManager.addDiscuss(body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addDiscussComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        addDiscussError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net",s);
//                        Gson gson = new Gson();
//                        UploadImageBean bean = gson.fromJson(s, UploadImageBean.class);
//                        uploadImageSuccess(bean);
                        Map res = Utils.getMapForJson(s);
                        int code = (int) res.get("code");
                        if (code == 0){
                            addDiscussSuccess();
                        }else {
                            addDiscussError();
                        }
                    }
                })
        );

    }

    private void addDiscussSuccess(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.addDiscussSuccess();
        }
    }
    private void addDiscussError(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.addDiscussError();
        }
    }
    private void addDiscussComplete(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.addDiscussComplete();
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
                        Log.e("777777single",s);
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
        DiscussChatAddView view = getView();
        if (view != null){
            view.uploadImageSuccess(bean);
        }
    }
    private void uploadImageError(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.uploadImageError();
        }
    }
    private void uploadImageCompleted(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.uploadImageCompleted();
        }
    }

    @Override
    public void editDiscuss(RequestBody body) {
        compositeSubscription.add(msgDataManager.editDiscuss(body).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                                editDiscussComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                editDiscussError();
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666",s);
//                        Gson gson = new Gson();
//                        UploadImageBean bean = gson.fromJson(s, UploadImageBean.class);
//                        uploadImageSuccess(bean);
                                Map res = Utils.getMapForJson(s);
                                int code = (int) res.get("code");
                                if (code == 0){
                                    editDiscussSuccess();
                                }else {
                                    editDiscussError();
                                }
                            }
                        })
        );
    }

    private void editDiscussSuccess(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.editDiscussSuccess();
        }
    }
    private void editDiscussError(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.editDiscussError();
        }
    }
    private void editDiscussComplete(){
        DiscussChatAddView view = getView();
        if (view != null){
            view.editDiscussComplete();
        }
    }


}
