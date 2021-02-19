package com.wd.winddots.desktop.list.goods.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.bean.GoodsListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: GoodsListAdapter
 * Author: 郑
 * Date: 2020/7/24 12:09 PM
 * Description:
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsListBean.GoodsListItem, BaseViewHolder> {
    public GoodsListAdapter(int layoutResId, @Nullable List<GoodsListBean.GoodsListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListBean.GoodsListItem item) {
        ImageView icon = helper.getView(R.id.icon);
        if (item.getPhoto() != null) {
            String imageurl = item.getPhoto();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        } else {
            icon.setImageResource(R.mipmap.default_img);
        }
        helper.setText(R.id.tv_1, item.getGoodsNameNo()).
                setText(R.id.tv_2, item.getNumber()).
                setText(R.id.tv_3, Utils.nullOrEmpty(item.getAttr1())).
                setText(R.id.tv_4, Utils.nullOrEmpty(item.getAttr2())).
                setText(R.id.tv_5, "备注信息: " + Utils.nullOrEmpty(item.getDescription()));

    }
}
