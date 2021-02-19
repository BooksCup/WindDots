package com.wd.winddots.mvp.presenter.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wd.winddots.MyApplication;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.ApprovalProcessBean;
import com.wd.winddots.bean.resp.ExamineApproveBean;
import com.wd.winddots.mvp.presenter.ApprovalProcessPresenter;
import com.wd.winddots.mvp.view.ApprovalProcessView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.SpHelper;

import java.util.zip.Adler32;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ApprovalProcessPresenterImpl extends BasePresenter<ApprovalProcessView> implements ApprovalProcessPresenter {
    public final ElseDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public ApprovalProcessPresenterImpl() {
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 初始化数据
     * @param id
     * @param userId
     * @param type
     */
    @Override
    public void loadData(String id, String userId, final String type) {
        compositeSubscription.add(dataManager.loadApprovalPocessData(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                        onLoadComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadError(e.getMessage());
                        onLoadComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {
                            Gson gson = new Gson();
                            ApprovalProcessBean bean = new ApprovalProcessBean();
                            if (type.equals("0") || type.equals("1") || type.equals("2") || type.equals("3")){//请假加班
                                ApprovalProcessBean.TimeData timeDean = gson.fromJson(data, ApprovalProcessBean.TimeData.class);
                                if (timeDean != null){
                                    bean.setTimeData(timeDean);
                                    onLoadSuccess(bean);
                                } else {
                                    onLoadError("加载数据为空");
                                }
                            } else {//报销
                                ApprovalProcessBean.MoneyData moneyData = gson.fromJson(data, ApprovalProcessBean.MoneyData.class);
                                if (moneyData != null){
                                    bean.setMoneyData(moneyData);
                                    onLoadSuccess(bean);
                                } else {
                                    onLoadError("加载数据为空");
                                }
                            }
                        }
                    }
                }));
    }

    public void onLoadSuccess(ApprovalProcessBean bean){
        ApprovalProcessView view = getView();
        if (view == null) return;

        view.onLoadSuccess(bean);
    }

    public void onLoadError(String errMsg){
        ApprovalProcessView view = getView();
        if (view == null) return;

        view.onLoadError(errMsg);
    }

    public void onLoadComplete(){
        ApprovalProcessView view = getView();
        if (view == null) return;

        view.onLoadComplete();
    }


    /**
     * 审批 同意或驳回
     * @param userId
     * @param applyId
     * @param applyStatus
     * @param applyRemark
     */
    @Override
    public void examineApprove(String userId, String applyId, String applyStatus, String applyRemark,String enterpriseId) {
        compositeSubscription.add(dataManager.examineApprove(userId, applyId, applyStatus, applyRemark,enterpriseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onExamineApproveComplete();
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onExamineApproveError(e.getMessage());
                        onExamineApproveComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        onExamineApproveSuccess(data);
                    }
                })
        );
    }

    private void onExamineApproveSuccess(String data){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onExamineApproveSuccess(data);
    }

    private void onExamineApproveError(String errMsg){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onExamineApproveError(errMsg);
    }

    private void onExamineApproveComplete(){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onExamineApproveComplete();
    }


    /**
     * 撤回 审批
     * @param userId
     * @param id
     */
    @Override
    public void recallApproval(String userId, String id) {
        compositeSubscription.add(dataManager.recallApprove(userId, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        onRecallApprovalComplete();
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onRecallApprovalError("撤回失败: " + e.getMessage());
                        onRecallApprovalComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        onRecallApprovalSuccess(data);
                    }
                })
        );
    }

    private void onRecallApprovalSuccess(String data){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onRecallApprovalSuccess(data);
    }

    private void onRecallApprovalError(String errMsg){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onRecallApprovalError(errMsg);
    }

    private void onRecallApprovalComplete(){
        ApprovalProcessView view = getView();
        if (view == null) return;
        view.onRecallApprovalComplete();
    }

}
