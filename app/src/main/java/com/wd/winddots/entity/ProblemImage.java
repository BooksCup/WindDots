package com.wd.winddots.entity;

import java.util.List;

/**
 * FileName: ProblemImage
 * Author: 郑
 * Date: 2021/3/9 11:37 AM
 * Description:
 */
public class ProblemImage {

    private String tag;
    private String images;
    private List<String> photos;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
