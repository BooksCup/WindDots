package com.wd.winddots.bean.param;

public class PostImg {
    private String uri;
    private String name;
    private String type;

    public PostImg() {
    }

    public PostImg(String uri, String name, String type) {
        this.uri = uri;
        this.name = name;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
