package com.wd.winddots.desktop.list.invoice.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceDetailBean;
import com.wd.winddots.desktop.list.invoice.presenter.InvoiceDetailPresenter;
import com.wd.winddots.desktop.list.invoice.presenter.view.InvoiceDetailView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: InvoiceDetailPresenterImpl
 * Author: 郑
 * Date: 2020/7/13 3:00 PM
 * Description:
 */
public class InvoiceDetailPresenterImpl extends BasePresenter<InvoiceDetailView> implements InvoiceDetailPresenter {

    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public InvoiceDetailPresenterImpl() {
        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void getInvoiceDetail(String id) {
        compositeSubscription.add(dataManager.getInvoiceDetail(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getInvoiceDetailComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        getInvoiceDetailError();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Gson gson = new Gson();
                        InvoiceDetailBean bean = gson.fromJson(s, InvoiceDetailBean.class);
                        if (bean.getCode() == 0) {
                            getInvoiceDetailSuccess(bean);
                        }else {
                            getInvoiceDetailError();
                        }
                    }
                })
        );
    }

    private void getInvoiceDetailSuccess(InvoiceDetailBean invoiceDetailBean) {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.getInvoiceDetailSuccess(invoiceDetailBean);
        }
    }

    private void getInvoiceDetailError() {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.getInvoiceDetailError();
        }
    }

    private void getInvoiceDetailComplete() {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.getInvoiceDetailComplete();
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
                        Log.e("net666", s);
                        Map res = Utils.getMapForJson(s);
                        if (res != null) {
                            int code = (int) res.get("code");
                            if (code == 0) {
                                addInvoiceSuccess();
                            } else {
                                addInvoiceError();
                            }
                        } else {
                            addInvoiceError();
                        }
                    }
                })
        );
    }

    private void addInvoiceSuccess() {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.addInvoiceSuccess();
        }
    }

    private void addInvoiceError() {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.addInvoiceError();
        }
    }

    private void addInvoiceComplete() {
        InvoiceDetailView view = getView();
        if (view != null) {
            view.addInvoiceComplete();
        }
    }


}
