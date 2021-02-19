package com.wd.winddots.desktop.list.quote.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.quote.bean.QuoteListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: QuoteListAdapter
 * Author: 郑
 * Date: 2020/7/20 10:57 AM
 * Description:
 */
public class QuoteListAdapter extends BaseQuickAdapter<QuoteListBean.QuoteListItem, BaseViewHolder> {
    public QuoteListAdapter(int layoutResId, @Nullable List<QuoteListBean.QuoteListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuoteListBean.QuoteListItem item) {

        ImageView icon = helper.getView(R.id.icon);
        if (item.getQuoteImage() != null) {
            String imageurl =  item.getQuoteImage();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }
        String enterpriseName = "往来单位: " + Utils.nullOrEmpty(item.getToEnterpriseName());
        helper.setText(R.id.tv_1, item.getMaterialNo()).
                setText(R.id.tv_2, item.getQuotePrice()).
                setText(R.id.tv_3, item.getQuoteNumber()).
                setText(R.id.tv_4, item.getQuoteTime()).
                setText(R.id.tv_5, Utils.nullOrEmpty(item.getToEnterpriseName())).
                setText(R.id.tv_6, item.getQuoteRate());
    }
}
