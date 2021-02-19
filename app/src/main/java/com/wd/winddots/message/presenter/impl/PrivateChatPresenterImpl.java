package com.wd.winddots.message.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.UploadOssImageBean;
import com.wd.winddots.bean.param.MessageBody;
import com.wd.winddots.bean.param.SendMessageParam;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.message.presenter.PrivateChatPresenter;
import com.wd.winddots.message.presenter.view.PrivateChatView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PrivateChatPresenterImpl extends BasePresenter<PrivateChatView> implements PrivateChatPresenter {
    public final MsgDataManager dataManager;
    public final ElseDataManager elseDataManager;
    public final CompositeSubscription compositeSubscription;

    public PrivateChatPresenterImpl() {
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 初始化 数据列表
     *
     * @param fromId
     * @param convrId
     * @param jCreateTime
     */
    @Override
    public void initMessage(String fromId, String toId,String convrId, long jCreateTime) {
        compositeSubscription.add(dataManager.getPrivateChatMessage(fromId, toId,convrId, jCreateTime, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        initMessageComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        initMessageError(e.getMessage());
                        initMessageComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666",data);
                        if (!TextUtils.isEmpty(data)) {
                            Gson gson = new Gson();
                            PrivateChatHistoryBean privateChatHistoryBean = gson.fromJson(data, PrivateChatHistoryBean.class);
                            if (privateChatHistoryBean != null) {
                                initMessageSuccess(privateChatHistoryBean);
                            } else {
                                initMessageError("数据格式转化失败");
                            }
                        } else {
                            initMessageError("聊天记录为空");
                        }
                    }
                }));
    }

    public void initMessageSuccess(PrivateChatHistoryBean bean) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistorySuccess(bean);
    }

    public void initMessageError(String errMsg) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistoryError(errMsg);
    }

    public void initMessageComplete() {
        PrivateChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistoryComplete();
    }


    /**
     * 加载更多数据
     *
     * @param fromId
     * @param convrId
     * @param jCreateTime
     */
    @Override
    public void loadMoreMessage(String fromId,String toId, String convrId, long jCreateTime) {
        compositeSubscription.add(dataManager.getPrivateChatMessage(fromId, toId,convrId, jCreateTime, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        loadMoreMessageComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadMoreMessageError(e.getMessage());
                        loadMoreMessageComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        if (!TextUtils.isEmpty(data)) {
                            Gson gson = new Gson();
                            PrivateChatHistoryBean privateChatHistoryBean = gson.fromJson(data, PrivateChatHistoryBean.class);
                            if (privateChatHistoryBean != null) {
                                loadMoreMessageSuccess(privateChatHistoryBean);
                            } else {
                                loadMoreMessageError("数据格式转化失败");
                            }
                        } else {
                            loadMoreMessageError("聊天记录为空");
                        }
                    }
                }));

    }

    public void loadMoreMessageSuccess(PrivateChatHistoryBean bean) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistorySuccess(bean);
    }

    public void loadMoreMessageError(String errMsg) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistoryError(errMsg);
    }

    public void loadMoreMessageComplete() {
        PrivateChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistoryComplete();
    }


    /**
     * 发送消息
     *
     * @param fromId
     * @param targetId
     * @param body
     * @param targetType
     * @param msgType
     */
    @Override
    public void sendMessage(String fromId, String targetId, String body, String targetType, final String msgType) {
//        SendMessageParam param = new SendMessageParam();
//
//        param.setFromId(fromId);
//        param.setTargetId(targetId);
//        param.setBody(body);
//        param.setTargetType(targetType);
//        param.setMsgType(msgType);

        Map bodyMap = new HashMap();
        bodyMap.put("body", body);
        bodyMap.put("fromId", fromId);
        bodyMap.put("targetId", targetId);
        bodyMap.put("targetType", targetType);
        bodyMap.put("msgType", msgType);
        bodyMap.put("questionId","custom_message");

        Gson gson = new Gson();
        String paramS = gson.toJson(bodyMap);
        Log.e("net666paramS",paramS);

        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramS);

        Logg.d("post img  data :" + paramS);

        compositeSubscription.add(dataManager.sendMessage(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666sendmessage" , String.valueOf(e));
                        onSendMessageError(msgType, e.getMessage());
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666sendmessage" , data);
                        onSendMessageSuccess(msgType);
                    }
                }));
    }


    public void onSendMessageSuccess(String msgType) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.postMessageSuccess(msgType);
    }

    public void onSendMessageError(String msgType, String errMsg) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.postMessageError(msgType, errMsg);
    }


    /**
     * 文本
     *
     * @param fromId
     * @param targetId
     * @param text
     * @param targetType
     * @param msgType
     */
    @Override
    public void postText(String fromId, String targetId, String text, String targetType, String msgType) {
        MessageBody.TextBody textBody = new MessageBody.TextBody();
        textBody.setExtras(new MessageBody.TextBody.ExtrasBean());
        textBody.setText(text);
        String body = new Gson().toJson(textBody);

        sendMessage(fromId, targetId, body, targetType, msgType);
    }


    /**
     * 上传 图片
     */
    @Override
    public void postImage(RequestBody body, final String fromId, final String targetId) {
        compositeSubscription.add(elseDataManager.uploadBigOSSImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        postImageError(e.getMessage());
                        Log.e("net666postImage" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666postImage","" + data);

                        Gson gson = new Gson();
                        UploadOssImageBean imageBean = gson.fromJson(data,UploadOssImageBean.class);
                        if (data != null && "0".equals(imageBean.getCode())){
                            postImageSuccess(imageBean.getList().get(0), fromId, targetId);
                        }
                    }
                }));
    }

    public void postImageSuccess(String data,String fromId,String targetId) {

        Map customMessage = new HashMap();
        Map imageData = new HashMap();
        String imageUrl = "http:" + data;
        imageData.put("image",imageUrl);
        imageData.put("msgType","image");
        //imageData.put("url","2");
        customMessage.put("extras",imageData);
        customMessage.put("text",imageUrl);

        Map body = customMessage;//Utils.getListForJson(data).get(0);
        Gson gson = new Gson();
        String jsonS = gson.toJson(body);
        sendMessage(fromId,targetId,jsonS,"single","image_message");


    }

    public void postImageError(String errMsg) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.postImageError(errMsg);
    }


    /**
     * 上传 语音
     */
    @Override
    public void postVoice(RequestBody body, final String fromId, final String targetId, final int timeLong) {
        compositeSubscription.add(elseDataManager.uploadBigOSSImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        postImageError(e.getMessage());
                        Log.e("net666postImage" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666postImage","" + data);

                        Gson gson = new Gson();
                        UploadOssImageBean imageBean = gson.fromJson(data,UploadOssImageBean.class);
                        if (data != null && "0".equals(imageBean.getCode())){
                            postVoiceSuccess(imageBean.getList().get(0), fromId, targetId,timeLong);
                        }
                    }
                }));
    }

    public void postVoiceSuccess(String data,String fromId,String targetId,int timeLong) {

        Map customMessage = new HashMap();
        Map imageData = new HashMap();
        String imageUrl = "http:" + data;
        imageData.put("voice",imageUrl);
        imageData.put("msgType","voice");
        imageData.put("timeLong",timeLong);
        //imageData.put("url","2");
        customMessage.put("extras",imageData);
        customMessage.put("text",imageUrl);
        customMessage.put("timeLong",timeLong);

        Map body = customMessage;//Utils.getListForJson(data).get(0);
        Gson gson = new Gson();
        String jsonS = gson.toJson(body);
        sendMessage(fromId,targetId,jsonS,"single","voice_message");


    }

    public void postVoiceError(String errMsg) {
        PrivateChatView view = getView();
        if (view == null) return;
        view.postVoiceError(errMsg);
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
        PrivateChatView view = getView();
        if (view == null) return;
        view.getChatBottomItemSuccess(bean);
    }
    private void getChatBottomItemError(String errMsg){
        PrivateChatView view = getView();
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
