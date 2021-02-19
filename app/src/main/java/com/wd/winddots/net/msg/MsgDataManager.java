package com.wd.winddots.net.msg;


import com.wd.winddots.net.RetrofitService;

import java.util.List;


import okhttp3.RequestBody;
import rx.Observable;

public class MsgDataManager {
    private RetrofitService mRetrofitService;

    public MsgDataManager() {
        this.mRetrofitService = MsgRetrofitHelper.getInstance().getServer();
    }


    /**
     * 检查更新
     *
     * @return
     */
    public Observable<String> upgrade() {
        return mRetrofitService.upgrade(1, 1, "winddots");
    }


    /**
     * 消息 私聊 列表
     *
     * @param fromId
     * @return
     */
    public Observable<String> loadPrivateChatListData(String fromId) {
        return mRetrofitService.loadPrivateChatListData(fromId);
    }

    /**
     * 消息 群聊 列表
     *
     * @param viewId
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<String> loadGroupChatListData(String viewId, int page, int pageSize) {
        return mRetrofitService.loadGroupChatListData(viewId, page, pageSize);
    }


    /**
     * 私聊 聊天历史记录
     *
     * @param fromId
     * @param convrId
     * @param jCreateTime
     * @param pageSize
     * @return
     */
    public Observable<String> getPrivateChatMessage(String fromId, String toId, String convrId, long jCreateTime, int pageSize) {
        return mRetrofitService.getPrivateChatMessage(fromId, toId, convrId, jCreateTime, pageSize);
    }

    /**
     * 离开群组页面
     */
    public Observable<String> leaveGroup(String fromId,  String convrId) {
        return mRetrofitService.leaveGroup(fromId, convrId);
    }


    /**
     * 发送消息
     *
     * @param body
     * @return
     */
    public Observable<String> sendMessage(RequestBody body) {
        return mRetrofitService.sendMessage(body);
    }

    /**
     * 发送消息
     *
     * @param body
     * @return
     */
    public Observable<String> atMembers(String userId,String groupId, RequestBody body) {
        return mRetrofitService.atMembers(userId,groupId,body);
    }

    /**
     * 发送消息
     *
     * @param
     * @return
     */
    public Observable<String> outGroupPerson(String userId,String groupId) {
        return mRetrofitService.outGroupPerson(userId,groupId);
    }



    /**
     * 获取群成员
     *
     * @param groupId
     * @return
     */
    public Observable<String> getGroupMembers(String groupId) {
        return mRetrofitService.getGroupMembers(groupId);
    }


    /**
     * 获取聊天界面图片链接
     *
     * @param mediaId
     * @return
     */
    public Observable<String> getImage(String mediaId) {
        return mRetrofitService.getImage(mediaId);
    }

    /**
     * 聊天上传图片
     *
     * @param userId
     * @return
     */
    public Observable<String> postImage(String userId, RequestBody map) {
        return mRetrofitService.postImage(userId, map);
    }

    /**
     * 获取讨论社区界面的列表数据
     *
     * @param id
     * @param userId
     * @return
     */
    public Observable<String> getDiscussMessage(String id, String userId) {
        return mRetrofitService.getDiscussMessage(id, userId);
    }


    /**
     * 获取个人信息
     *
     * @param id
     * @param fromUserId
     * @return
     */
    public Observable<String> getUserInfo(String id, String fromUserId) {
        return mRetrofitService.getUserInfo(id, fromUserId);
    }

    /**
     * 获取积分列表
     *
     * @param id
     * @return
     */
    public Observable<String> getPoints(String id) {
        return mRetrofitService.getPoints(id);
    }

    /**
     * 添加会务
     *
     * @param body
     * @return
     */
    public Observable<String> addDiscuss(RequestBody body) {
        return mRetrofitService.addDiscuss(body);
    }

    /**
     * 编辑会务
     *
     * @param body
     * @return
     */
    public Observable<String> editDiscuss(RequestBody body) {
        return mRetrofitService.editDiscuss(body);
    }


    /**
     * 添加会务评论
     *
     * @param body
     * @return
     */
    public Observable<String> addDiscussMessage(RequestBody body) {
        return mRetrofitService.addDiscussMessage(body);
    }

    /**
     * 会务添加人员
     *
     * @param
     * @return
     */
    public Observable<String> discussAddUsers(String questionId, List<String> userIds) {
        return mRetrofitService.discussAddUsers(questionId, userIds);
    }

    /**
     * 删除会务
     *
     * @param
     * @return
     */
    public Observable<String> deleteQuestion(String questionId) {
        return mRetrofitService.deleteQuestion(questionId);
    }

    /**
     * 开启或关闭会务
     *
     * @param
     * @return
     */
    public Observable<String> updateQuestionStatus(RequestBody body) {
        return mRetrofitService.updateQuestionStatus(body);
    }

    /**
     * 获取好友列表
     *
     * @param
     * @return
     */
    public Observable<String> getFriendList(String userId, String enterpriseId) {
        return mRetrofitService.getFriendList(userId, enterpriseId);
    }

    /**
     * 删除好友
     *
     * @param
     * @return
     */
    public Observable<String> deleteFriend(String userId, String friendUserId) {
        return mRetrofitService.deleteFriend(userId, friendUserId);
    }


    /**
     * 发送好友请求
     *
     * @param
     * @return
     */
    public Observable<String> postFriendApplies(String fromUserId, String toUserId) {
        return mRetrofitService.postFriendApplies(fromUserId, toUserId);
    }

    /**
     * 发送好友请求
     *
     * @param
     * @return
     */
    public Observable<String> agreeFriendApplies(String applyId,
                                                 int status,
                                                 String fromUserId,
                                                 String fromEnterpriseId,
                                                 String toUserId,
                                                 String toEnterpriseId) {
        return mRetrofitService.agreeFriendApplies(applyId, status, fromUserId, fromEnterpriseId, toUserId, toEnterpriseId);
    }


    /**
     * 创建群聊
     *
     * @param
     * @return
     */
    public Observable<String> addGroupChat(String owner,
                                           String groupName,
                                           String userIds,
                                           String desc) {
        return mRetrofitService.addGroupChat(owner,groupName,userIds,desc);
    }

    /**
     * 添加群成员
     *
     * @param
     * @return
     */
    public Observable<String> addGroupMembers(String groupId,
                                           String addUserIds) {
        return mRetrofitService.addGroupMembers(groupId,addUserIds);
    }


    /**
     * 删除群成员
     *
     * @param
     * @return
     */
    public Observable<String> deleteGroupMembers(String groupId,
                                              String removeUserIds) {
        return mRetrofitService.deleteGroupMembers(groupId,removeUserIds);
    }






}
