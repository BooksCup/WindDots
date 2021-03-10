package com.wd.winddots.entity;

import java.io.Serializable;

/**
 * 物品规格
 *
 * @author zhou
 */
public class GoodsSpec implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建者用户ID
     */
    private String useId;

    /**
     * 物品主键ID
     */
    private String goodsId;

    /**
     * 规格1的值
     */
    private String x;

    /**
     * 规格2的值
     */
    private String y;

    /**
     * 是否显示
     */
    private String display;

    /**
     * 排序
     */
    private int sort;

    private String num;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 企业ID
     */
    private String enterpriseId;

    private String applyNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }
}
