package com.wd.winddots.message.bean;

import android.net.Uri;

import java.util.List;

public class GroupChatHistoryBean {

    private List<AvatarMapBean> avatarMap;
    private List<MessageListBean> messageList;

    public List<AvatarMapBean> getAvatarMap() {
        return avatarMap;
    }

    public void setAvatarMap(List<AvatarMapBean> avatarMap) {
        this.avatarMap = avatarMap;
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }

    public static class AvatarMapBean {
        /**
         * userAvatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad2abfb54844d4fac7720d6981833dc.jpg
         * userId : 059e71da12ee4d3dab97e3510d184972
         */


        private String id;
        private String name;
        private String avatar;
        private String type;
        private boolean select;
        private int isOwner;

        public int getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(int isOwner) {
            this.isOwner = isOwner;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        //        private String userAvatar;
//        private String userId;
//
//
//
//        public String getUserAvatar() {
//            return userAvatar;
//        }
//
//        public void setUserAvatar(String userAvatar) {
//            this.userAvatar = userAvatar;
//        }
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public void setUserId(String userId) {
//            this.userId = userId;
//        }
    }

    public static class MessageListBean {
        /**
         * id : d4f2c3852ed8453badf3161c7f4ad815
         * convrId :
         * type :
         * targetType : group
         * fromType : admin
         * msgType : custom_message
         * fromId : a2c3b2f6d32345b2818be757f5adb54f
         * targetId : 5fad1dd016474f5b82c7b968d7c556a0
         * body : {"extras":{"image":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/2b37d44ef76c485eb767b1ccaad586ec.JPG","url":"11111","publicNumberId":"1231827215562444800"},"text":"11111"}
         * createTime : 2020-02-24 14:24:29
         * jId : 6366521180
         * jCreateTime : 1582525469264
         * relevantId :
         * toUserName :
         * toUserAvatar :
         * fromUserName : 郑可超
         * fromUserAvatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f81d3a54fa7d40339d6d84c4414b55c2.jpg
         */

        private String id;
        private String convrId;
        private String type;
        private String targetType;
        private String fromType;
        private String msgType;
        private String fromId;
        private String targetId;
        private String body;
        private String createTime;
        private long jId;
        private long jCreateTime;
        private String relevantId;
        private String toUserName;
        private String toUserAvatar;
        private String fromUserName;
        private String fromUserAvatar;
        private String realImgUrl;
        private String showTime;
        private Uri imageUri;
        private Uri voiceUri;
        private String voiceUrl;
        private String playStatus;
        private int audioLength;

        public String getPlayStatus() {
            return playStatus;
        }

        public void setPlayStatus(String playStatus) {
            this.playStatus = playStatus;
        }

        public Uri getVoiceUri() {
            return voiceUri;
        }

        public void setVoiceUri(Uri voiceUri) {
            this.voiceUri = voiceUri;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public int getAudioLength() {
            return audioLength;
        }

        public void setAudioLength(int audioLength) {
            this.audioLength = audioLength;
        }

        public Uri getImageUri() {
            return imageUri;
        }

        public void setImageUri(Uri imageUri) {
            this.imageUri = imageUri;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public long getjId() {
            return jId;
        }

        public void setjId(long jId) {
            this.jId = jId;
        }

        public long getjCreateTime() {
            return jCreateTime;
        }

        public void setjCreateTime(long jCreateTime) {
            this.jCreateTime = jCreateTime;
        }

        public String getRealImgUrl() {
            return realImgUrl;
        }

        public void setRealImgUrl(String realImgUrl) {
            this.realImgUrl = realImgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConvrId() {
            return convrId;
        }

        public void setConvrId(String convrId) {
            this.convrId = convrId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public String getFromType() {
            return fromType;
        }

        public void setFromType(String fromType) {
            this.fromType = fromType;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getJId() {
            return jId;
        }

        public void setJId(long jId) {
            this.jId = jId;
        }

        public long getJCreateTime() {
            return jCreateTime;
        }

        public void setJCreateTime(long jCreateTime) {
            this.jCreateTime = jCreateTime;
        }

        public String getRelevantId() {
            return relevantId;
        }

        public void setRelevantId(String relevantId) {
            this.relevantId = relevantId;
        }

        public String getToUserName() {
            return toUserName;
        }

        public void setToUserName(String toUserName) {
            this.toUserName = toUserName;
        }

        public String getToUserAvatar() {
            return toUserAvatar;
        }

        public void setToUserAvatar(String toUserAvatar) {
            this.toUserAvatar = toUserAvatar;
        }

        public String getFromUserName() {
            return fromUserName;
        }

        public void setFromUserName(String fromUserName) {
            this.fromUserName = fromUserName;
        }

        public String getFromUserAvatar() {
            return fromUserAvatar;
        }

        public void setFromUserAvatar(String fromUserAvatar) {
            this.fromUserAvatar = fromUserAvatar;
        }
    }
}
