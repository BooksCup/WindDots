package com.wd.winddots.message.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.UploadOssImageBean;
import com.wd.winddots.bean.param.MessageBody;
import com.wd.winddots.bean.param.SendMessageParam;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.GroupChatListBean;
import com.wd.winddots.message.presenter.GroupChatPresenter;
import com.wd.winddots.message.presenter.view.GroupChatView;
import com.wd.winddots.message.presenter.view.PrivateChatView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GroupChatPresenterImpl extends BasePresenter<GroupChatView> implements GroupChatPresenter {
    public final MsgDataManager dataManager;
    public final ElseDataManager elseDataManager;
    public final CompositeSubscription compositeSubscription;

    public GroupChatPresenterImpl() {
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void initMessage(String fromId, String convrId, long jCreateTime) {
        compositeSubscription.add(dataManager.getPrivateChatMessage(fromId,"" ,convrId, jCreateTime, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        initChatMessageHistoryComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        initChatMessageHistoryError("获取消息失败: " + e.getMessage());
                        initChatMessageHistoryComplete();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666",data);
                        if (!TextUtils.isEmpty(data)){
                            Gson gson = new Gson();
                            GroupChatHistoryBean groupChatHistoryBean = gson.fromJson(data, GroupChatHistoryBean.class);
                            initChatMessageHistorySuccess(groupChatHistoryBean);
                        } else {
                            initChatMessageHistoryError("获取消息数据为空");
                        }
                    }
                }));
    }



    private void initChatMessageHistorySuccess(GroupChatHistoryBean bean){
        GroupChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistorySuccess(bean);
    }

    private void initChatMessageHistoryError(String errMsg){
        GroupChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistoryError(errMsg);
    }

    private void initChatMessageHistoryComplete(){
        GroupChatView view = getView();
        if (view == null) return;
        view.initChatMessageHistoryComplete();
    }


    /**
     * 加载更多聊天记录
     * @param fromId
     * @param convrId
     * @param jCreateTime
     */
    @Override
    public void loadMoreMessage(String fromId, String convrId, long jCreateTime) {
        compositeSubscription.add(dataManager.getPrivateChatMessage(fromId,"", convrId, jCreateTime, 10)
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
                            GroupChatHistoryBean groupChatHistoryBean = gson.fromJson(data, GroupChatHistoryBean.class);
                            if (groupChatHistoryBean != null) {
                                loadMoreMessageSuccess(groupChatHistoryBean);
                            } else {
                                loadMoreMessageError("数据格式转化失败");
                            }
                        } else {
                            loadMoreMessageError("聊天记录为空");
                        }
                    }
                }));

    }


    public void loadMoreMessageSuccess(GroupChatHistoryBean bean) {
        GroupChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistorySuccess(bean);
    }

    public void loadMoreMessageError(String errMsg) {
        GroupChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistoryError(errMsg);
    }

    public void loadMoreMessageComplete() {
        GroupChatView view = getView();
        if (view == null) return;
        view.loadMoreChatMessageHistoryComplete();
    }


    /**
     * 发送消息
     * @param fromId
     * @param groupId
     * @param body
     * @param targetType
     * @param msgType
     */
    @Override
    public void sendMessage(String fromId, String groupId, String body, String targetType, String msgType) {
        SendMessageParam param = new SendMessageParam();

        param.setFromId(fromId);
        param.setTargetId(groupId);
        param.setBody(body);
        param.setTargetType(targetType);
        param.setMsgType(msgType);

        Gson gson = new Gson();
        String paramS = gson.toJson(param);
        Log.e("net666",paramS);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramS);

        compositeSubscription.add(dataManager.sendMessage(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        postMessageComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666sendMessage" , e.getMessage());
                        postMessageError();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666sendMessage" , data);
                        postMessageSuccess();
                    }
                }));
    }


    public void postMessageSuccess() {
        GroupChatView view = getView();
        if (view == null) return;
        view.postMessageSuccess();
    }

    public void postMessageError() {
        GroupChatView view = getView();
        if (view == null) return;
        view.postMessageError();
    }

    public void postMessageComplete() {
        GroupChatView view = getView();
        if (view == null) return;
        view.postMessageComplete();
    }

    @Override
    public void postText(String fromId, String groupId, String text, String targetType, String msgType) {
        MessageBody.TextBody textBody = new MessageBody.TextBody();
        textBody.setExtras(new MessageBody.TextBody.ExtrasBean());
        textBody.setText(text);
        String body = new Gson().toJson(textBody);

        sendMessage(fromId, groupId, body, targetType, msgType);
    }

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
                        Log.e("post img e:" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666","" + data);

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
        sendMessage(fromId,targetId,jsonS,"group","image_message_group");
    }

    public void postImageError(String errMsg) {
        GroupChatView view = getView();
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
        sendMessage(fromId,targetId,jsonS,"group","voice_message_group");


    }

    public void postVoiceError(String errMsg) {
        GroupChatView view = getView();
        if (view == null) return;
        view.postVoiceError(errMsg);
    }

    @Override
    public void getGroupMembers(String groupId) {
        compositeSubscription.add(dataManager.getGroupMembers(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getGroupMembersComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        postImageError(e.getMessage());
                        Logg.d("net666" + e.getMessage());
                        getGroupMembersError();
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666",data);
                        Gson gson = new Gson();
                        List<GroupChatHistoryBean.AvatarMapBean> list = gson.fromJson(data, new TypeToken<List<GroupChatHistoryBean.AvatarMapBean>>() {
                                                    }.getType());

                        getGroupMembersSuccess(list);

                    }
                }));
    }


    // 获取群成员
    private void getGroupMembersSuccess(List<GroupChatHistoryBean.AvatarMapBean> list){
        GroupChatView view = getView();
        if (view == null) return;
        view.getGroupMembersSuccess(list);

    }
    private void getGroupMembersError(){
        GroupChatView view = getView();
        if (view == null) return;
        view.getGroupMembersError();
    }
    private void getGroupMembersComplete(){
        GroupChatView view = getView();
        if (view == null) return;
        view.getGroupMembersComplete();
    }


    @Override
    public void leaveGroup(String fromId, String convrId) {
        compositeSubscription.add(dataManager.leaveGroup(fromId,convrId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        getGroupMembersComplete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666",data);
                    }
                }));
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
        GroupChatView view = getView();
        if (view == null) return;
        view.getChatBottomItemSuccess(bean);
    }
    private void getChatBottomItemError(String errMsg){
        GroupChatView view = getView();
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

    @Override
    public void atMembers(String userId, String groupId, RequestBody body) {
        compositeSubscription.add(dataManager.atMembers(userId,groupId,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getChatBottomItemError(e.getMessage());
                        Log.e("net666atMembers" , String.valueOf(e));
                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666atMembers", data);
//                        Gson gson = new Gson();
//                        ChatBottomBean bean = gson.fromJson(data,ChatBottomBean.class);
//                        getChatBottomItemSuccess(bean);
                    }
                }));
    }


}
