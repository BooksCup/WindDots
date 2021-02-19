package com.wd.winddots.desktop.list.warehouse.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.warehouse.adapter.WarehouseTapAdapter;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseListBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import androidx.viewpager.widget.ViewPager;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: WarehouseDetailActivity
 * Author: 郑
 * Date: 2020/7/31 4:23 PM
 * Description: 仓库详情
 */
public class WarehouseDetailActivity extends BaseActivity {

    private WarehouseListBean.WarehouseListItem warehouseListItem;


    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private WarehouseTapAdapter mTabAdapter;

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;


    @Override
    public int getContentView() {
        return R.layout.activity_warehouse_detail;
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            warehouseListItem = gson.fromJson(data, WarehouseListBean.WarehouseListItem.class);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        String title2 = mContext.getString(R.string.warehouse_contact) + ": " + Utils.nullOrEmpty(warehouseListItem.getContactName())
                + " " + Utils.nullOrEmpty(warehouseListItem.getContactPhone());
        String title3 = mContext.getString(R.string.warehouse_address) + ": " + Utils.nullOrEmpty(warehouseListItem.getArea())
                + " " + Utils.nullOrEmpty(warehouseListItem.getAddress());
        tv1.setText(warehouseListItem.getName());
        tv2.setText(title2);
        tv3.setText(title3);

        mTablayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPager);

        mTabAdapter = new WarehouseTapAdapter(getSupportFragmentManager(),warehouseListItem.getId());
        mViewPage.setAdapter(mTabAdapter);
        mTablayout.setupWithViewPager(mViewPage);
        mViewPage.setCurrentItem(0);

        LinearLayout header = findViewById(R.id.ll_header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
