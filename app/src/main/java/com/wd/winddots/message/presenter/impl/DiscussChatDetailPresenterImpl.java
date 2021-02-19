package com.wd.winddots.message.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.message.presenter.DiscussChatDetailPresenter;
import com.wd.winddots.message.presenter.view.DiscussChatDetailView;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Utils;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: DiscussChatDetailPresenterImpl
 * Author: 郑
 * Date: 2020/6/16 9:52 AM
 * Description:
 */
public class DiscussChatDetailPresenterImpl extends BasePresenter<DiscussChatDetailView> implements DiscussChatDetailPresenter {

    public final MsgDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public DiscussChatDetailPresenterImpl() {
        dataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void initMessage(String id, String userId) {
        compositeSubscription.add(dataManager.getDiscussMessage(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        initMessageDataListComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net", String.valueOf(e));
                        initMessageDataListError("加载失败: " + e.getMessage());
                        initMessageDataListComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net", data);
                        if (!TextUtils.isEmpty(data)) {
                            DiscussChatBean discussChatBean = new Gson().fromJson(data, DiscussChatBean.class);
                            if (discussChatBean != null) {
                                initMessageDataListSuccess(discussChatBean);
                            } else {
                                initMessageDataListError("数据封装失败");
                            }
                        } else {
                            initMessageDataListError("数据为空");
                        }
                    }
                }));
    }

    public void initMessageDataListSuccess(DiscussChatBean bean) {
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.initMessageDataListSuccess(bean);
    }

    public void initMessageDataListError(String errMsg) {
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.initMessageDataListError();
    }

    public void initMessageDataListComplete() {
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.initMessageDataListCompleted();
    }


    @Override
    public void addUsers(String id, List<String> userIds) {
        compositeSubscription.add(dataManager.discussAddUsers(id, userIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addUsersCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        addUsersError();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net", data);
                        Map res = Utils.getMapForJson(data);
                        int code = (int) res.get("code");
                        if (code == 0){
                            addUsersSuccess();
                        }else {
                            addUsersError();
                        }
                    }
                }));
    }

    void addUsersSuccess(){
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.addUsersSuccess();
    }
    void addUsersError(){
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.addUsersError();
    }
    void addUsersCompleted(){
        DiscussChatDetailView view = getView();
        if (view == null) return;
        view.addUsersCompleted();
    }

}
