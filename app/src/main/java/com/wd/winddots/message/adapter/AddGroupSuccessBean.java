package com.wd.winddots.message.adapter;

/**
 * FileName: AddGroupSuccessBean
 * Author: 郑
 * Date: 2020/8/26 12:19 PM
 * Description: 添加群聊成功
 */
public class AddGroupSuccessBean {

    private String avatar;
    private String convrId;
    private String id;
    private int flag;
    private long jId;
    private String name;
    private String owner;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getConvrId() {
        return convrId;
    }

    public void setConvrId(String convrId) {
        this.convrId = convrId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getjId() {
        return jId;
    }

    public void setjId(long jId) {
        this.jId = jId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
