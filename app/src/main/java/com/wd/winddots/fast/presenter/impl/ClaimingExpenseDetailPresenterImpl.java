package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.fast.presenter.ClaimingExpenseDetailPresenter;
import com.wd.winddots.fast.presenter.view.ClaimingExpenseDetailView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Logg;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: ClaimingExpenseDetailPresenterImpl
 * Author: 郑
 * Date: 2020/5/20 3:30 PM
 * Description:
 */
public class ClaimingExpenseDetailPresenterImpl extends BasePresenter<ClaimingExpenseDetailView> implements ClaimingExpenseDetailPresenter {


    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;


    public ClaimingExpenseDetailPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    /*
    * 更改发票类型
    * */
    @Override
    public void updateApplyInvoiceType(String applyId, String applyAddressId, String invoiceType) {

        compositeSubscription.add(dataManager.updateApplyInvoiceType(applyId,applyAddressId,invoiceType).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        updateApplyInvoiceTypeCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateApplyInvoiceTypeError();
                    }

                    @Override
                    public void onNext(String s) {
                        updateApplyInvoiceTypeSuccess();

                    }
                })
                );

    }
    private void updateApplyInvoiceTypeSuccess(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceTypeSuccess();
        }
    }
    private void updateApplyInvoiceTypeError(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceTypeError();
        }
    }
    private void updateApplyInvoiceTypeCompleted(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceTypeCompleted();
        }
    }


    /*
    * 更改发票照片
    * */
    @Override
    public void updateApplyInvoiceImage(String applyId, RequestBody requestBody) {
        compositeSubscription.add(dataManager.updateApplyInvoiceImage(applyId,requestBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        updateApplyInvoiceImageCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateApplyInvoiceImageError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("xxxxxxx",s);
                        updateApplyInvoiceImageSuccess();

                    }
                })
        );
    }

    private void updateApplyInvoiceImageSuccess(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceImageSuccess();
        }
    }
    private void updateApplyInvoiceImageError(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceImageError();
        }
    }
    private void updateApplyInvoiceImageCompleted(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.updateApplyInvoiceImageCompleted();
        }
    }


    /*
    * 上传照片
    * */
    @Override
    public void uploadImage(RequestBody body, final int position) {
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
                        if (bean.getList() != null && bean.getList().size() > 0){
                            uploadImageSuccess(bean,position);
                        }
                    }
                })
        );
    }

    void uploadImageSuccess(UploadImageBean bean,int position){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.uploadImageSuccess(bean, position);
        }
    }
    void uploadImageError(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.uploadImageError();
        }
    }
    void uploadImageCompleted(){
        ClaimingExpenseDetailView view = getView();
        if (view != null){
            view.uploadImageCompleted();
        }
    }





}
