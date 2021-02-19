package com.wd.winddots.business.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.business.bean.RateCurrencyBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: RateCurrencyAdapter
 * Author: 郑
 * Date: 2020/8/20 10:08 AM
 * Description: 汇率
 */
public class RateCurrencyAdapter extends BaseQuickAdapter<RateCurrencyBean.RateCurrencyItem, BaseViewHolder> {
    public RateCurrencyAdapter(int layoutResId, @Nullable List<RateCurrencyBean.RateCurrencyItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RateCurrencyBean.RateCurrencyItem item) {
//        TextView tv4 = helper.getView(R.id.tv_content4);
//        tv4.setVisibility(View.GONE);

        String date = Utils.nullOrEmpty(item.getRatePublishDate());
        if (date.length() > 16){
            date = date.substring(5,16);
            date = date.replace(".","-");
            Log.e("net666",date);
        }
        helper.setText(R.id.tv_content1, Utils.nullOrEmpty(item.getRateCurrencyName()))
                .setText(R.id.tv_content2, Utils.nullOrEmpty(item.getRateCurrencyBuy())).
                setText(R.id.tv_content3, Utils.nullOrEmpty(item.getRateCurrencySell())).
                setText(R.id.tv_content4, Utils.nullOrEmpty(item.getRateMiddle())).
                setText(R.id.tv_content5, date);
    }
}
