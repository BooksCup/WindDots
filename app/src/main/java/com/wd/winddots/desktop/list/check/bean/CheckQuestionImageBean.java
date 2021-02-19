package com.wd.winddots.desktop.list.check.bean;

import android.net.Uri;

/**
 * FileName: CheckQuestionImageBean
 * Author: 郑
 * Date: 2021/1/15 3:34 PM
 * Description:
 */
public class CheckQuestionImageBean {

    private String imageUrl;
    private Uri imageUri;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
