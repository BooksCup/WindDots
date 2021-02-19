package com.wd.winddots.desktop.list.order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.order.adapter.OrderDetailTapAdapter;
import com.wd.winddots.desktop.list.order.bean.OrderListBean;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import androidx.viewpager.widget.ViewPager;

/**
 * FileName: OrderDetailActivity
 * Author: 郑
 * Date: 2020/8/7 5:10 PM
 * Description: 订单详情
 */
public class OrderDetailActivity extends BaseActivity {


    private OrderListBean.OrderListItem orderListItem;

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private OrderDetailTapAdapter mTabAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            orderListItem = gson.fromJson(data, OrderListBean.OrderListItem.class);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        TextView tv4 = findViewById(R.id.tv_header4);
        TextView tv5 = findViewById(R.id.tv_header5);

        ImageView icon = findViewById(R.id.iv_header_icon);
        if (orderListItem.getPhoto() != null) {
            String imageurl =  orderListItem.getPhoto();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }

        String goodsNoName = Utils.nullOrEmpty(orderListItem.getGoodsNo()) + " (" + Utils.nullOrEmpty(orderListItem.getGoodsName())+ ")";
        String theme = mContext.getString(R.string.order_theme) + Utils.nullOrEmpty(orderListItem.getThemeTitle());
        String number = mContext.getString(R.string.order_number) + Utils.numberNullOrEmpty(orderListItem.getDeliveryCount()) + "/" + Utils.numberNullOrEmpty(orderListItem.getApplyCount());
        String dateS = Utils.nullOrEmpty(orderListItem.getCreateTime());
        if (dateS.length() > 10){
            dateS = dateS.substring(0,10);
        }else {
            dateS = "";
        }
        String date = mContext.getString(R.string.order_date) + dateS;

        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        String enterpriseName = "";
        if (enterpriseId.equals(orderListItem.getFromEnterpriseId())) {
            enterpriseName = Utils.nullOrEmpty(orderListItem.getToEnterpriseName());
        } else {
            enterpriseName = Utils.nullOrEmpty(orderListItem.getFromEnterpriseName());
        }
        String customer = mContext.getString(R.string.order_customer) + enterpriseName;
        tv1.setText(goodsNoName);
        tv2.setText(theme);
        tv3.setText(number);
        tv4.setText(date);
        tv5.setText(customer);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        mTablayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPager);

        mTabAdapter = new OrderDetailTapAdapter(getSupportFragmentManager(),orderListItem.getId());
        mViewPage.setAdapter(mTabAdapter);
        mTablayout.setupWithViewPager(mViewPage);
        mViewPage.setCurrentItem(0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
