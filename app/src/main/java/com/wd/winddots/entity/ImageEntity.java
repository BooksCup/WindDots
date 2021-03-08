package com.wd.winddots.entity;

/**
 * 图片
 *
 * @author zhou
 */
public class ImageEntity {

    private String id;

    ///图片本地路径
    private String path;

    ///图片网络地址
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
