package com.wd.winddots.desktop.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.view.filter.FilterBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: ListBottomBarAdapter
 * Author: 郑
 * Date: 2020/7/7 2:15 PM
 * Description:
 */
public class ListBottomBarAdapter extends BaseQuickAdapter<FilterBean, BaseViewHolder> {
    public ListBottomBarAdapter(int layoutResId, @Nullable List<FilterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterBean item) {
        helper.setText(R.id.tv_content,item.getValue());
    }
}
