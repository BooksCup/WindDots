package com.wd.winddots.entity;

import com.wd.winddots.activity.work.PicBean;

import java.util.List;

/**
 * FileName: FabricCheckTaskLot
 * Author: 郑
 * Date: 2021/2/23 11:48 AM
 * Description:
 */
public class FabricCheckProblem {

    private String id;
    private String lengthBefore;
    private String weightBefore;
    private List<ImageEntity> imageEntities;
    private boolean select = false;
    private String tag;
    private String level;
    private String tagATimes;
    private String tagBTimes;
    private String tagCTimes;
    private String tagDTimes;
    private String image;
    private List<String> images;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTagATimes() {
        return tagATimes;
    }

    public void setTagATimes(String tagATimes) {
        this.tagATimes = tagATimes;
    }

    public String getTagBTimes() {
        return tagBTimes;
    }

    public void setTagBTimes(String tagBTimes) {
        this.tagBTimes = tagBTimes;
    }

    public String getTagCTimes() {
        return tagCTimes;
    }

    public void setTagCTimes(String tagCTimes) {
        this.tagCTimes = tagCTimes;
    }

    public String getTagDTimes() {
        return tagDTimes;
    }

    public void setTagDTimes(String tagDTimes) {
        this.tagDTimes = tagDTimes;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<ImageEntity> getImageEntities() {
        return imageEntities;
    }

    public void setImageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLengthBefore() {
        return lengthBefore;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public String getWeightBefore() {
        return weightBefore;
    }

    public void setWeightBefore(String weightBefore) {
        this.weightBefore = weightBefore;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
