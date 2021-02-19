package com.wd.winddots.message.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.message.bean.PrivateChatListBean;
import com.wd.winddots.message.presenter.PrivateChatTabPresenter;
import com.wd.winddots.message.presenter.view.PrivateChatTabView;
import com.wd.winddots.net.msg.MsgDataManager;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PrivateChatTabPresenterImpl extends BasePresenter<PrivateChatTabView> implements PrivateChatTabPresenter {

    public final MsgDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public PrivateChatTabPresenterImpl() {
        dataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadHeaderData() {

    }

    @Override
    public void loadListData(String fromId) {
        compositeSubscription.add(dataManager.loadPrivateChatListData(fromId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onLoadListDataComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadListDataError("加载数据错误!");
                        onLoadListDataComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {
                            Log.e("net666",data);
                            Gson gson = new Gson();
                            List<PrivateChatListBean> list = gson.fromJson(data, new TypeToken<List<PrivateChatListBean>>() {
                            }.getType());
//                            Logg.d("list: " + list.toString());
                            if (list != null) {
                                onLoadListDataSuccess(list);
                            } else {
                                onLoadListDataError("加载数据错误!");
                            }
                        }

                    }
                }));
    }


    public void onLoadListDataSuccess(List<PrivateChatListBean> privateChatListBeanList) {
        PrivateChatTabView view = getView();
        if (view == null) return;
        view.onPrivateChatListSuccess(privateChatListBeanList);
    }


    public void onLoadListDataError(String errorMsg) {
        PrivateChatTabView view = getView();
        if (view == null) return;
        view.onPrivateChatListError(errorMsg);
    }

    public void onLoadListDataComplete(){
        PrivateChatTabView view = getView();
        if (view == null) return;
        view.onPrivateChatListComplete();
    }
}
