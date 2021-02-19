package com.wd.winddots.desktop.list.invoice.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.desktop.list.invoice.presenter.InvoiceAddPresenter;
import com.wd.winddots.desktop.list.invoice.presenter.view.InvoiceAddView;
import com.wd.winddots.fast.bean.CostBean;
import com.wd.winddots.fast.bean.CurrencyBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.selector.SelectBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: InvoiceAddPresenterImpl
 * Author: 郑
 * Date: 2020/7/9 10:03 AM
 * Description:
 */
public class InvoiceAddPresenterImpl extends BasePresenter<InvoiceAddView> implements InvoiceAddPresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public InvoiceAddPresenterImpl(){
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    /*
    * 获取币种列表
    * */
    @Override
    public void getCurrencyList(String enterpriseId) {
        compositeSubscription.add(dataManager.getCurrencyList(enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getCurrencyListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getCurrencyListerror();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Gson gson = new Gson();
                        CurrencyBean bean = gson.fromJson(s,CurrencyBean.class);
                        getCurrencyListSuccess(bean.getData());

                    }
                })
        );
    }
    private void getCurrencyListSuccess(List<SelectBean> currencyList){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCurrencyListSuccess(currencyList);
        }
    }
    private  void getCurrencyListerror(){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCurrencyListerror();
        }
    }
    private  void getCurrencyListCompleted(){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCurrencyListComplete();
        }
    }



    @Override
    public void getCostTypeList(String enterpriseId) {
        compositeSubscription.add(dataManager.getCostList(enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getCostTypeListComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getCostTypeListError();
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        CostBean bean = gson.fromJson(s, CostBean.class);
                        getCostTypeListSuccess(bean.getData());
                    }
                })
        );
    }

    private void getCostTypeListSuccess(List<SelectBean> currencyList){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCostTypeListSuccess(currencyList);
        }
    }
    private  void getCostTypeListError(){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCostTypeListError();
        }
    }
    private  void getCostTypeListComplete(){
        InvoiceAddView view = getView();
        if (view != null){
            view.getCostTypeListComplete();
        }
    }




    @Override
    public void upLoadImage(RequestBody body, final int index) {
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
                            uploadImageSuccess(bean,index);
                        }else {
                            uploadImageError();
                        }

                    }
                })
        );
    }


    private void uploadImageSuccess(UploadImageBean bean,int index){
        InvoiceAddView view = getView();
        if (view != null){
            view.uploadImageSuccess(bean,index);
        }
    }
    private void uploadImageError(){
        InvoiceAddView view = getView();
        if (view != null){
            view.uploadImageError();
        }
    }
    private void uploadImageCompleted(){
        InvoiceAddView view = getView();
        if (view != null){
            view.uploadImageCompleted();
        }
    }





    @Override
    public void addinvoice(RequestBody body) {
        compositeSubscription.add(dataManager.addInvoice(body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addInvoiceComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        addInvoiceError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Map res = Utils.getMapForJson(s);
                        if (res != null){
                            int code = (int) res.get("code");
                            if (code == 0){
                                addInvoiceSuccess();
                            }else {
                                addInvoiceError();
                            }
                        }else {
                            addInvoiceError();
                        }
                    }
                })
        );
    }

    private void addInvoiceSuccess(){
        InvoiceAddView view = getView();
        if (view != null){
            view.addInvoiceSuccess();
        }
    }
    private void addInvoiceError(){
        InvoiceAddView view = getView();
        if (view != null){
            view.addInvoiceError();
        }
    }
    private void addInvoiceComplete(){
        InvoiceAddView view = getView();
        if (view != null){
            view.addInvoiceComplete();
        }
    }

}
