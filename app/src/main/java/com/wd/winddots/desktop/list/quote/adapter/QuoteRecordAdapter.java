package com.wd.winddots.desktop.list.quote.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.quote.bean.QuoteDetailBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: QuoteDetailAdapter
 * Author: 郑
 * Date: 2020/7/21 10:24 AM
 * Description:
 */
public class QuoteRecordAdapter extends BaseQuickAdapter<QuoteDetailBean.QuoteDetailItem, BaseViewHolder> {
    public QuoteRecordAdapter(int layoutResId, @Nullable List<QuoteDetailBean.QuoteDetailItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuoteDetailBean.QuoteDetailItem item) {

        LinearLayout body = helper.getView(R.id.ll_body);
        if (item.isSelect()) {
            body.setBackgroundColor(mContext.getResources().getColor(R.color.colorF0));
        } else {
            body.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
        helper.setText(R.id.tv_1, item.getTitle1()).
                setText(R.id.tv_2, item.getTitle2()).
                setText(R.id.tv_3, item.getTitle3()).
                setText(R.id.tv_4, item.getTitle4()).
                setText(R.id.tv_5, item.getTitle5());
    }
}
