package com.wd.winddots.mvp.presenter.impl;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.GroupChatListBean;
import com.wd.winddots.mvp.presenter.GroupChatTabPresenter;
import com.wd.winddots.mvp.view.GroupChatTabView;
import com.wd.winddots.net.msg.MsgDataManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GroupChatTabPresenterImpl extends BasePresenter<GroupChatTabView> implements GroupChatTabPresenter {
    public final MsgDataManager dataManager;
    public final CompositeSubscription compositeSubscription;
    private int PAGESIZE = 10;

    public GroupChatTabPresenterImpl() {
        dataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void refreshGroupChatListData(String id) {
        compositeSubscription.add(dataManager.loadGroupChatListData(id, 1, PAGESIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onRefreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onRefreshError("刷新数据失败!");

                        onRefreshComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {
                            Gson gson = new Gson();
                            GroupChatListBean groupChatListBean = gson.fromJson(data, GroupChatListBean.class);
//                            List<GroupChatListBean.DataBean> list = gson.fromJson(data, new TypeToken<List<GroupChatListBean.DataBean>>() {
//                            }.getType());
                            if (groupChatListBean != null) {
                                onRefreshSuccess(groupChatListBean);
                            } else {
                                onRefreshError("刷新数据失败!");
                            }
                        }
                    }
                }));
    }

    @Override
    public void loadMoreGroupChatListData(String id, int page) {
        compositeSubscription.add(dataManager.loadGroupChatListData(id, page, PAGESIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onLoadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadMoreError("加载更多数据失败!");

                        onLoadMoreComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {
                            Gson gson = new Gson();
                            GroupChatListBean groupChatListBean = gson.fromJson(data, GroupChatListBean.class);
                            if (groupChatListBean != null) {
                                onLoadMoreSuccess(groupChatListBean);
                            } else {
                                onLoadMoreError("加载更多数据失败!");
                            }
                        }

                    }
                }));

    }


    public void onRefreshSuccess(GroupChatListBean bean) {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListRefreshSuccess(bean);
    }

    public void onRefreshError(String msg) {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListRefreshError(msg);
    }

    public void onRefreshComplete() {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListRefreshComplete();
    }

    public void onLoadMoreSuccess(GroupChatListBean bean) {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListLoadMoreSuccess(bean);
    }

    public void onLoadMoreError(String msg) {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListLoadMoreError(msg);
    }

    public void onLoadMoreComplete() {
        GroupChatTabView view = getView();
        if (view == null) return;
        view.onGroupChatListLoadMoreComplete();
    }
}
