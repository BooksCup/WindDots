package com.wd.winddots.desktop.list.goods.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.bean.GoodsTypeBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: GoodsTypeAdapter
 * Author: 郑
 * Date: 2020/7/27 2:39 PM
 * Description:
 */
public class GoodsTypeAdapter extends BaseQuickAdapter <GoodsTypeBean.GoodsTypeItem, BaseViewHolder>{

    public GoodsTypeAdapter(int layoutResId, @Nullable List<GoodsTypeBean.GoodsTypeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsTypeBean.GoodsTypeItem item) {
        LinearLayout body = helper.getView(R.id.ll_body);
        if (item.isSelect()) {
            body.setBackgroundColor(mContext.getResources().getColor(R.color.colorF0));
        } else {
            body.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
        helper.setText(R.id.tv_name,item.getName());
    }
}
