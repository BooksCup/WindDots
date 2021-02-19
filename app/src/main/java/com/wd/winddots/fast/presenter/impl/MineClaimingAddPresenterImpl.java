package com.wd.winddots.fast.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.bean.CurrencyBean;
import com.wd.winddots.fast.presenter.MineClaimingAddPresenter;
import com.wd.winddots.fast.presenter.view.MineClaimingAddView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.selector.SelectBean;

import java.util.List;
import java.util.Map;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineClaimingAddPresenterImpl
 * Author: 郑
 * Date: 2020/5/6 5:57 PM
 * Description:
 */
public class MineClaimingAddPresenterImpl extends BasePresenter<MineClaimingAddView> implements MineClaimingAddPresenter {



    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public MineClaimingAddPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }


    /*
    * 获取币种
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
                        Gson gson = new Gson();
                        CurrencyBean bean = gson.fromJson(s,CurrencyBean.class);
                        getCurrencyListSuccess(bean.getData());

                    }
                })
                );

    }
    private void getCurrencyListSuccess(List<SelectBean> currencyList){
        MineClaimingAddView view = getView();
        if (view != null){
            view.getCurrencyListSuccess(currencyList);
        }
    }
    private  void getCurrencyListerror(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.getCurrencyListerror();
        }
    }
    private  void getCurrencyListCompleted(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.getCurrencyListCompleted();
        }
    }


    /*
    * 新建报销
    * */
    @Override
    public void addClaiming(RequestBody body,String enterpriseId) {
        compositeSubscription.add(dataManager.addApply(body,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addClaimingCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        addClaimingError();
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            addClaimingError();
                        }else if (s.length() == 10){
                            addClaimingtSuccess();
                        }else {
                            addClaimingError();
                        }
                    }
                })
                );
    }
    private void addClaimingtSuccess(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.addClaimingtSuccess();
        }
    }
    private  void addClaimingError(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.addClaimingError();
        }
    }
    private  void addClaimingCompleted(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.addClaimingCompleted();
        }
    }

    @Override
    public void updateClaiming(RequestBody body,String enterpriseId) {
        compositeSubscription.add(dataManager.updateApply(body,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        addClaimingCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        updateClaimingError();
                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            updateClaimingError();
                        }else if (s.length() == 10){
                            updateClaimingtSuccess();
                        }else {
                            updateClaimingError();
                        }
                    }
                })
        );
    }
    private void updateClaimingtSuccess(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.updateClaimingtSuccess();
        }
    }
    private  void updateClaimingError(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.updateClaimingError();
        }
    }
    private  void updateClaimingCompleted(){
        MineClaimingAddView view = getView();
        if (view != null){
            view.updateClaimingCompleted();
        }
    }


}
