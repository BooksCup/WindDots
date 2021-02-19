package com.wd.winddots.message.bean;

public class PrivateChatListBean {

    /**
     * id : 780af2618c184326805cb04b1cff0e53
     * fromId : a2c3b2f6d32345b2818be757f5adb54f
     * toId : 14f871a7b8dc493faf9e7dd3779f00be
     * type : 0
     * targetType : group
     * text :
     * timeStamp : 1571015715000
     * createTime : 2019-10-14 09:15:15
     * unread : 0
     * isTop : 01
     * userId : 41071862
     * userName : 郑可超、胡骏
     * userAvatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/6d96790967984021be281623e93012cb.jpg
     * singleName :
     * singleJobName :
     * companyName :
     * shortName :
     * departmentName :
     */

    private String id;
    private String fromId;
    private String toId;
    private String type;
    private String targetType;
    private String text;
    private long timeStamp;
    private String createTime;
    private int unread;
    private String isTop;
    private String userId;
    private String userName;
    private String userAvatar;
    private String singleName;
    private String singleJobName;
    private String companyName;
    private String shortName;
    private String departmentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public String getSingleJobName() {
        return singleJobName;
    }

    public void setSingleJobName(String singleJobName) {
        this.singleJobName = singleJobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "PrivateChatListBean{" +
                "id='" + id + '\'' +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", type='" + type + '\'' +
                ", targetType='" + targetType + '\'' +
                ", text='" + text + '\'' +
                ", timeStamp=" + timeStamp +
                ", createTime='" + createTime + '\'' +
                ", unread=" + unread +
                ", isTop='" + isTop + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", singleName='" + singleName + '\'' +
                ", singleJobName='" + singleJobName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
