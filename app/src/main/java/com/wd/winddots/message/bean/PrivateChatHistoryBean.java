package com.wd.winddots.message.bean;

import android.net.Uri;

import java.util.List;

public class PrivateChatHistoryBean {

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

        private String userAvatar;
        private String userId;

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "AvatarMapBean{" +
                    "userAvatar='" + userAvatar + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }

    public static class MessageListBean {
        /**
         * id : c5439582edc84a52948f3733ffab3815
         * convrId :
         * type :
         * targetType : single
         * fromType : admin
         * msgType : image text
         * fromId : a2c3b2f6d32345b2818be757f5adb54f
         * targetId : 059e71da12ee4d3dab97e3510d184972
         * body : {"mediaId":"qiniu/image/j/56A6D9CC30A6A1F8EF3F6A67936427C2.jpg","mediaCrc32":1906874777,"width":720,"height":1280,"format":"jpg","fsize":261651}
         * createTime : 2020-04-15 14:07:43
         * showTime : "2020-04-15 14:07"
         * jId : 6950392334
         * jCreateTime : 1586930862891
         * relevantId :
         * toUserName : 丁二辉
         * toUserAvatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad2abfb54844d4fac7720d6981833dc.jpg
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
        private String showTime;
        private long jId;
        private long jCreateTime;
        private String relevantId;
        private String toUserName;
        private String toUserAvatar;
        private String fromUserName;
        private String fromUserAvatar;
        private Uri imageUri;
        private Uri voiceUri;
        private String voiceUrl;
        private int audioLength;

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

        private String playStatus;

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

        public Uri getImageUri() {
            return imageUri;
        }

        public void setImageUri(Uri imageUri) {
            this.imageUri = imageUri;
        }

        private String realImgUrl;

        public String getRealImgUrl() {
            return realImgUrl;
        }

        public void setRealImgUrl(String realImgUrl) {
            this.realImgUrl = realImgUrl;
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

        @Override
        public String toString() {
            return "MessageListBean{" +
                    "id='" + id + '\'' +
                    ", convrId='" + convrId + '\'' +
                    ", type='" + type + '\'' +
                    ", targetType='" + targetType + '\'' +
                    ", fromType='" + fromType + '\'' +
                    ", msgType='" + msgType + '\'' +
                    ", fromId='" + fromId + '\'' +
                    ", targetId='" + targetId + '\'' +
                    ", body='" + body + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", jId=" + jId +
                    ", jCreateTime=" + jCreateTime +
                    ", relevantId='" + relevantId + '\'' +
                    ", toUserName='" + toUserName + '\'' +
                    ", toUserAvatar='" + toUserAvatar + '\'' +
                    ", fromUserName='" + fromUserName + '\'' +
                    ", fromUserAvatar='" + fromUserAvatar + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PrivateChatHistoryBean{" +
                "avatarMap=" + avatarMap +
                ", messageList=" + messageList +
                '}';
    }
}
