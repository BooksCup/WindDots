package com.wd.winddots.business.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.business.bean.MaterialsPriceBean;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: MaterialsPriceAdapter
 * Author: 郑
 * Date: 2020/8/19 3:27 PM
 * Description:
 */
public class MaterialsPriceAdapter extends BaseQuickAdapter<BusinessBean.MaterialsPrice, BaseViewHolder> {
    public MaterialsPriceAdapter(int layoutResId, @Nullable List<BusinessBean.MaterialsPrice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessBean.MaterialsPrice item) {

//        TextView tv4 = helper.getView(R.id.tv_content4);
//        //tv4.setVisibility(View.GONE);
//
//        String date = Utils.nullOrEmpty(item.getPriceCreateTime());
//        if (date.length() > 10){
//            date = date.substring(0,10);
//        }
//
        String priceChange = Utils.numberNullOrEmpty(item.getChange());

        Number n1;
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        try {
            n1  = numberFormat.parse(priceChange);
        } catch (Exception e) {
            n1 = 0;
            e.printStackTrace();
        }

        TextView changeTV = helper.getView(R.id.tv_content4);
        assert n1 != null;
        if (n1.floatValue() > 0){
            changeTV.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }else if (n1.floatValue() < 0){
            changeTV.setTextColor(mContext.getResources().getColor(R.color.colorStyleG));
        }else {
            changeTV.setTextColor(mContext.getResources().getColor(R.color.color50));
        }
//
//

        String date = "";
        if (item.getDate() != null && item.getDate().length() >= 10){
            date = item.getDate().substring(5,10);
        }

        helper.setText(R.id.tv_content1, Utils.nullOrEmpty(item.getName())).
                setText(R.id.tv_content2, Utils.nullOrEmpty(item.getLastTrade())).
                setText(R.id.tv_content3, Utils.nullOrEmpty(item.getUnit())).
                setText(R.id.tv_content4, Utils.nullOrEmpty(item.getChange())).
                setText(R.id.tv_content5, date);

    }
}
