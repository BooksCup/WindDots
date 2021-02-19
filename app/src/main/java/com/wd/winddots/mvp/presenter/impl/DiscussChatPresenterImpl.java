package com.wd.winddots.mvp.presenter.impl;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.mvp.presenter.DiscussChatPresenter;
import com.wd.winddots.mvp.view.DiscussChatView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DiscussChatPresenterImpl extends BasePresenter<DiscussChatView> implements DiscussChatPresenter {

    public final MsgDataManager dataManager;
    public final ElseDataManager elseDataManager;
    public final CompositeSubscription compositeSubscription;

    public DiscussChatPresenterImpl() {
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }


    /**
     * 初始化消息列表数据
     *
     * @param id     列表id
     * @param userId 用户id
     */
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

                        Log.e("net",data);
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
        DiscussChatView view = getView();
        if (view == null) return;
        view.initMessageDataListSuccess(bean);
    }

    public void initMessageDataListError(String errMsg) {
        DiscussChatView view = getView();
        if (view == null) return;
        view.initMessageDataListError(errMsg);
    }

    public void initMessageDataListComplete() {
        DiscussChatView view = getView();
        if (view == null) return;
        view.initMessageDataListComplete();
    }


    @Override
    public void sendMessage(RequestBody body) {
        compositeSubscription.add(dataManager.addDiscussMessage(body)
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
                        Log.e("net",data);
                    }
                }));
    }

    /*
     * 删除会务
     * */
    @Override
    public void deleteDiscuss(String id) {
        compositeSubscription.add(dataManager.deleteQuestion(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        deleteDiscussComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net", String.valueOf(e));
                        deleteDiscussError();
                        initMessageDataListComplete();
                    }
                    @Override
                    public void onNext(String data) {
                        Map res = Utils.getMapForJson(data);
                        if (res == null){
                            deleteDiscussError();
                        }
                        int code = (int) res.get("code");
                        if (code == 0){
                            deleteDiscussSuccess();
                        }else {
                            deleteDiscussError();
                        }
                    }
                }));
    }

    private void deleteDiscussSuccess(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.deleteDiscussSuccess();
    }
    private void deleteDiscussError(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.deleteDiscussError();
    }
    private void deleteDiscussComplete(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.deleteDiscussComplete();
    }



    @Override
    public void updateDiscussStatus(String id, int questionStatus) {
        Map data = new HashMap();
        data.put("id",id);
        data.put("questionStatus",questionStatus);
        RequestBody body = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.updateQuestionStatus(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        deleteDiscussComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateDiscussError();
                    }
                    @Override
                    public void onNext(String data) {
                        Map res = Utils.getMapForJson(data);
                        if (res == null){
                            updateDiscussError();
                        }
                        int code = (int) res.get("code");
                        if (code == 0){
                            updateDiscussSuccess();
                        }else {
                            updateDiscussError();
                        }
                    }
                }));
    }
    private void updateDiscussSuccess(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.updateDiscussSuccess();
    }
    private void updateDiscussError(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.updateDiscussError();
    }
    private void updateDiscussComplete(){
        DiscussChatView view = getView();
        if (view == null) return;
        view.updateDiscussComplete();
    }


    @Override
    public void getChatBottomItem(String userId, int module) {
        compositeSubscription.add(elseDataManager.getChatBottomItem(userId,module)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getChatBottomItemError(e.getMessage());
                        Log.e("net666getChatBottomItem" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666getChatBottomItem", data);
                        Gson gson = new Gson();
                        ChatBottomBean bean = gson.fromJson(data,ChatBottomBean.class);
                        getChatBottomItemSuccess(bean);
                    }
                }));
    }



    private void getChatBottomItemSuccess(ChatBottomBean bean){
        DiscussChatView view = getView();
        if (view == null) return;
        view.getChatBottomItemSuccess(bean);
    }
    private void getChatBottomItemError(String errMsg){
        DiscussChatView view = getView();
        if (view == null) return;
        view.getChatBottomItemError(errMsg);
    }
    private  void getChatBottomItemErrorComplete(){

    }


    @Override
    public void chatBottomItemSort(String userId, RequestBody body) {
        compositeSubscription.add(elseDataManager.chatBottomItemSort(userId,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getChatBottomItemError(e.getMessage());
                        Log.e("net666chatBottomsort" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666chatBottomsort", data);
//                        Gson gson = new Gson();
//                        ChatBottomBean bean = gson.fromJson(data,ChatBottomBean.class);
//                        getChatBottomItemSuccess(bean);
                    }
                }));
    }



}
