package com.wd.winddots.desktop.list.contact.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.contact.adapter.ContractDetailTapAdapter;
import com.wd.winddots.desktop.list.contact.bean.ContractListBean;
import com.wd.winddots.desktop.list.order.bean.OrderNumberBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: OrderDetailActivity
 * Author: 郑
 * Date: 2020/8/7 5:10 PM
 * Description: 合同详情
 */
public class ContractDetailActivity extends BaseActivity {


    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private ContractListBean.ContractListItem orderListItem;

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private ContractDetailTapAdapter mTabAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_contract_detail;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            orderListItem = gson.fromJson(data, ContractListBean.ContractListItem.class);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        TextView tv4 = findViewById(R.id.tv_header4);
        TextView tv5 = findViewById(R.id.tv_header5);

        ImageView icon = findViewById(R.id.iv_header_icon);
//        if (orderListItem.getPhoto() != null) {
//            String imageurl =  orderListItem.getPhoto();
//            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
//        }else {
//            icon.setImageResource(R.mipmap.default_img);
//        }

//        String goodsNoName = Utils.nullOrEmpty(orderListItem.getGoodsNo()) + " (" + Utils.nullOrEmpty(orderListItem.getGoodsName())+ ")";
//        String theme = mContext.getString(R.string.order_theme) + Utils.nullOrEmpty(orderListItem.getThemeTitle());
//        String number = mContext.getString(R.string.order_number) + Utils.numberNullOrEmpty(orderListItem.getDeliveryCount()) + "/" + Utils.numberNullOrEmpty(orderListItem.getApplyCount());
//        String dateS = Utils.nullOrEmpty(orderListItem.getCreateTime());
//        if (dateS.length() > 10){
//            dateS = dateS.substring(0,10);
//        }else {
//            dateS = "";
//        }
//        String date = mContext.getString(R.string.order_date) + dateS;
//
//        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
//        String enterpriseName = "";
//        if (enterpriseId.equals(orderListItem.getFromEnterpriseId())) {
//            enterpriseName = Utils.nullOrEmpty(orderListItem.getToEnterpriseName());
//        } else {
//            enterpriseName = Utils.nullOrEmpty(orderListItem.getFromEnterpriseName());
//        }
//        String customer = mContext.getString(R.string.order_customer) + enterpriseName;


        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        String str2 = "品名: " + Utils.nullOrEmpty(orderListItem.getGoodsNamesStr());
        String str3 = "金额: ¥" + Utils.numberNullOrEmpty(orderListItem.getContractAmount());
        String str4 = "";
        if (!StringUtils.isNullOrEmpty(orderListItem.getCreateTime())){
            if (orderListItem.getCreateTime().length() > 10){
                str4 = "时间: " + orderListItem.getCreateTime().substring(0,10);
            }else {
                str4 = "时间: " + orderListItem.getCreateTime();
            }
        }else {
            str4 = "时间: ";
        }
        String str5 = "";
        if ("P".equals(orderListItem.getContractType())) {
            if (enterpriseId.equals(orderListItem.getFromEnterpriseId())) {
                str5 = "供应: " + Utils.nullOrEmpty(orderListItem.getToEnterpriseName());
            } else {
                str5 = "客户: " + Utils.numberNullOrEmpty(orderListItem.getFromEnterpriseId());
            }
        } else {
            if (enterpriseId.equals(orderListItem.getFromEnterpriseId())) {
                str5 = "客户: " + Utils.nullOrEmpty(orderListItem.getToEnterpriseName());
            } else {
                str5 = "供应: " + Utils.numberNullOrEmpty(orderListItem.getFromEnterpriseId());
            }
        }

        String str6 = "";
        if (!StringUtils.isNullOrEmpty(orderListItem.getStatus())){
            switch (orderListItem.getStatus()) {
                case "0":
                    str6 = "未发送";
                    break;
                case "1":
                    str6 = "发送中";
                    break;
                case "2":
                    str6 = "已确认";
                    break;
                default:
                    str6 = "未发送";
            }
        }else{
            str6 = "未发送";
        }

        tv1.setText(Utils.nullOrEmpty(orderListItem.getMaterialNumberStr()));
        tv2.setText(str2);
        tv3.setText(str3);
        tv4.setText(str4);
        tv5.setText(str5);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        mTablayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPager);

        mTabAdapter = new ContractDetailTapAdapter(getSupportFragmentManager(),orderListItem.getId());
        mViewPage.setAdapter(mTabAdapter);
        mTablayout.setupWithViewPager(mViewPage);
        mViewPage.setCurrentItem(0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getData();
    }
    
    private void getData(){
        compositeSubscription.add(dataManager.getContractDetail(orderListItem.getId()).
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


                    }
                })
        );
    }
}
