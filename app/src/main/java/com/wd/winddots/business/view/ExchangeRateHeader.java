package com.wd.winddots.business.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.utils.Utils;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;

/**
 * FileName: ExchangeRateHeader
 * Author: 郑
 * Date: 2020/12/30 10:11 AM
 * Description: 汇率顶部
 */
public class ExchangeRateHeader extends RelativeLayout {


    BusinessBean.ExhangeRate mRate;

    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mRateTv;
    private TextView mChangeTv;
    private TextView mTodayTv;
    private TextView mYestodayTv;
    private TextView mHeightTv;
    private TextView mlowTv;

    private HeaderMaterialsMoreDidClickListener materialsMoreDidClickListener;

    public void setMaterialsMoreDidClickListener(HeaderMaterialsMoreDidClickListener listener) {
        materialsMoreDidClickListener = listener;
    }

    public ExchangeRateHeader(Context context) {
        super(context);
        initView();
    }

    public ExchangeRateHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ExchangeRateHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_exchange_rage_header, this, false);
        this.addView(view);
        mTitleTv = view.findViewById(R.id.tv_type);
        mTimeTv = view.findViewById(R.id.tv_time);
        mRateTv = view.findViewById(R.id.tv_rate);
        mChangeTv = view.findViewById(R.id.tv_change);
        mTodayTv = view.findViewById(R.id.tv_todayvalue);
        mYestodayTv = view.findViewById(R.id.tv_yestoday_value);
        mHeightTv = view.findViewById(R.id.tv_height_value);
        mlowTv = view.findViewById(R.id.tv_deep_value);
        LinearLayout headerTitle = view.findViewById(R.id.header_title);
        headerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialsMoreDidClickListener != null) {
                    materialsMoreDidClickListener.materialsMoreDidClick();
                }
            }
        });
    }


    public interface HeaderMaterialsMoreDidClickListener {
        void materialsMoreDidClick();
    }

    public void setData(BusinessBean.ExhangeRate hotRage) {
        mRate = hotRage;
        String title = Utils.nullOrEmpty(hotRage.getCurrencyName()) + "兑人民币(USDCNY)";

        mTitleTv.setText(title);
        mRateTv.setText(Html.fromHtml(hotRage.getCurrentPrice()));
        mChangeTv.setText(Html.fromHtml(hotRage.getChange()));
        mTimeTv.setText(hotRage.getCreateTime());
//        mTodayTv.setText(Html.fromHtml(hotRage.getTodayPriceHtml()));
//        mYestodayTv.setText(Html.fromHtml(hotRage.getYesterdayPriceHtml()));
//        mHeightTv.setText(Html.fromHtml(hotRage.getHighestPriceHtml()));
//        mlowTv.setText(Html.fromHtml(hotRage.getLowestPriceHtml()));
    }

}
