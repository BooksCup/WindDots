package com.wd.winddots.message.bean;

import java.util.List;

/**
 * FileName: ChatBottomBean
 * Author: 郑
 * Date: 2020/11/11 10:11 AM
 * Description: 聊天页面底部功能按钮
 */
public class ChatBottomBean {

    private int code;
    private List<ChatBottomItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ChatBottomItem> getData() {
        return data;
    }

    public void setData(List<ChatBottomItem> data) {
        this.data = data;
    }

    public class ChatBottomItem{
        private int chatType;
        private String id;
        private String imageUrl;
        private String type;
        private int sort;

        public int getChatType() {
            return chatType;
        }

        public void setChatType(int chatType) {
            this.chatType = chatType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }


}
