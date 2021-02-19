package com.wd.winddots.mvp.presenter.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.LoginBean;
import com.wd.winddots.mvp.presenter.ExamineApproveAdapterPresenter;
import com.wd.winddots.mvp.view.ExamineApproveAdapterView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Logg;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ExamineApproveAdapterPresenterImpl implements ExamineApproveAdapterPresenter {

    private ExamineApproveAdapterView view;
    public final ElseDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public ExamineApproveAdapterPresenterImpl(ExamineApproveAdapterView view) {
        this.view = view;

        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 审批
     */
    @Override
    public void examineApprove(String userId, String applyId, final String applyStatus, String applyRemark,String enterpriseId) {
        compositeSubscription.add(dataManager.examineApprove(userId,applyId,applyStatus,applyRemark,enterpriseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onExamineApproveError("审批失败: " + e.getMessage());

                    }

                    @Override
                    public void onNext(String data) {
                        if (!TextUtils.isEmpty(data)){
                            onExamineApproveSuccess(applyStatus);
                        } else {
                            onExamineApproveSuccess("审批失败");
                        }
                    }
                })
        );
    }

    public void onExamineApproveSuccess(String type){
        if (view == null) return;
        view.onExamineApproveSuccess(type);
    }

    public void onExamineApproveError(String msg){
        view.onExamineApproveError(msg);
    }


}
