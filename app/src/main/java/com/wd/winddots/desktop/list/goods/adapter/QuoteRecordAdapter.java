package com.wd.winddots.desktop.list.goods.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.bean.QuoteRecordBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: QuoteRecordAdapter
 * Author: 郑
 * Date: 2020/7/28 3:55 PM
 * Description: 报价记录
 */
public class QuoteRecordAdapter extends BaseQuickAdapter<QuoteRecordBean.QuoteRecordItem, BaseViewHolder> {
    public QuoteRecordAdapter(int layoutResId, @Nullable List<QuoteRecordBean.QuoteRecordItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuoteRecordBean.QuoteRecordItem item) {
        helper.setText(R.id.tv_1, Utils.nullOrEmpty(item.getIntercourseEnterprise())).
                setText(R.id.tv_2, Utils.nullOrEmpty(item.getQuotePrice())).
                setText(R.id.tv_3, Utils.nullOrEmpty(item.getQuotePerson())).
                setText(R.id.tv_4, Utils.nullOrEmpty(item.getQuoteTime()));
    }
}
