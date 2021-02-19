package com.wd.winddots.desktop.list.enterprise.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.list.enterprise.adapter.EnterpriseTapAdapter;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseListBean;
import com.wd.winddots.net.enterprise.EnterpeiseDataManager;
import com.wd.winddots.utils.Utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.viewpager.widget.ViewPager;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: EnterpriseDetailActivity
 * Author: 郑
 * Date: 2020/12/23 9:50 AM
 * Description: 企业详情
 */
public class EnterpriseDetailActivity extends CommonActivity {


    private CompositeSubscription compositeSubscription;
    private EnterpeiseDataManager dataManager;

    private TextView headerIv;


    EnterpriseListBean.EnterpriseItem enterpriseItem;

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private EnterpriseTapAdapter mTabAdapter;


    @Override
    public void initView() {
        super.initView();
        compositeSubscription = new CompositeSubscription();
        dataManager = new EnterpeiseDataManager();
        addBadyView(R.layout.activity_enterprise_detail);


        List<String> colors = new ArrayList<>();
        colors.add("#D0AE9D");
        colors.add("#CEFF9A");
        colors.add("#CCCAFF");
        colors.add("#87CCB0");
        colors.add("#8DA2CD");
        colors.add("#FECBCC");
        colors.add("#CCCCCC");
        colors.add("#CCCE99");
        colors.add("#012B99");
        colors.add("#FFFF0B");


        //mViewPage.setBackground();

        setTitleText("企业详情");

        mTablayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPager);

        mTabAdapter = new EnterpriseTapAdapter(getSupportFragmentManager());
        mViewPage.setAdapter(mTabAdapter);
        mTablayout.setupWithViewPager(mViewPage);
        mViewPage.setCurrentItem(0);
        headerIv = findViewById(R.id.iv_header);

        Random random = new Random();
        headerIv.setBackgroundColor(Color.parseColor(colors.get(random.nextInt(9))));

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            enterpriseItem = gson.fromJson(data, EnterpriseListBean.EnterpriseItem.class);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        TextView tv4 = findViewById(R.id.tv_header4);
        TextView tv6 = findViewById(R.id.tv_header6);
        Date d = new Date(enterpriseItem.getEstiblishTime());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(d);

        tv1.setText(enterpriseItem.getName());
        tv2.setText("法定代表人:" + enterpriseItem.getLegalPersonName());
        tv3.setText("注册资本:" + enterpriseItem.getRegCapital());
        tv4.setText(enterpriseItem.getRegStatus());
        tv6.setText("成立日期:" + date);


    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }


    private void getData() {

        compositeSubscription.add(dataManager.enterpriseDetail(enterpriseItem.getId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("net666", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {
                        //mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666", s);

                        Gson gson = new Gson();

                        EnterpriseDetailBean detailBean = gson.fromJson(s, EnterpriseDetailBean.class);
                        headerIv.setText(Utils.nullOrEmpty(detailBean.getTycCompany().getAlias()));
                        mTabAdapter.setData(detailBean);
                    }
                })
        );


    }


}
