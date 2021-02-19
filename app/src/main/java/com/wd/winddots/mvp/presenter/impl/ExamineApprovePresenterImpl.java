package com.wd.winddots.mvp.presenter.impl;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.ExamineApproveBean;
import com.wd.winddots.bean.resp.GroupChatListBean;
import com.wd.winddots.mvp.presenter.ExamineApprovePresenter;
import com.wd.winddots.mvp.view.ExamineApproveView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Logg;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ExamineApprovePresenterImpl extends BasePresenter<ExamineApproveView> implements ExamineApprovePresenter {
    public final ElseDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    private int PAGESIZE = 10;

    public ExamineApprovePresenterImpl() {
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void refreshExamineApproveListData(String id) {
        compositeSubscription.add(dataManager.loadExamineApproveListData(id, 1, PAGESIZE)
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
                            ExamineApproveBean examineApproveBean = gson.fromJson(data, ExamineApproveBean.class);
                            if (examineApproveBean != null) {
                                onRefreshSuccess(examineApproveBean);
                            } else {
                                onRefreshError("刷新数据失败!");
                            }
                        }
                    }
                }));

    }

    private void onRefreshSuccess(ExamineApproveBean bean) {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListRefreshSuccess(bean);
    }

    private void onRefreshError(String errorMsg) {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListRefreshError(errorMsg);
    }

    private void onRefreshComplete() {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListRefreshComplete();
    }

    @Override
    public void loadMoreExamineApproveListData(String id, int page) {
        compositeSubscription.add(dataManager.loadExamineApproveListData(id, page, PAGESIZE)
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
                            ExamineApproveBean examineApproveBean = gson.fromJson(data, ExamineApproveBean.class);
                            if (examineApproveBean != null) {
                                onLoadMoreSuccess(examineApproveBean);
                            } else {
                                onLoadMoreError("加载更多数据失败!");
                            }
                        }
                    }
                }));

    }



    private void onLoadMoreSuccess(ExamineApproveBean bean) {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListLoadMoreSuccess(bean);
    }

    private void onLoadMoreError(String errorMsg) {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListLoadMoreError(errorMsg);
    }

    private void onLoadMoreComplete() {
        ExamineApproveView view = getView();
        if (view == null) return;

        view.onExamineApproveListLoadMoreComplete();
    }


    @Override
    public void changeApplyReadStatus(String applyId) {

    }

}
