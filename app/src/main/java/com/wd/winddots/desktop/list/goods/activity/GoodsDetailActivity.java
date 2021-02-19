package com.wd.winddots.desktop.list.goods.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.goods.adapter.GoodsDetailTapAdapter;
import com.wd.winddots.desktop.list.goods.bean.GoodsDetailBean;
import com.wd.winddots.desktop.list.goods.bean.GoodsListBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: GoodsDetailActivity
 * Author: 郑
 * Date: 2020/7/28 10:49 AM
 * Description: 物品详情
 */
public class GoodsDetailActivity extends BaseActivity {

    private GoodsListBean.GoodsListItem goodsListItem;

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private GoodsDetailTapAdapter mTabAdapter;

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;


    @Override
    public int getContentView() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            goodsListItem = gson.fromJson(data, GoodsListBean.GoodsListItem.class);
        }
        ImageView icon = findViewById(R.id.iv_header_icon);
        if (goodsListItem.getPhoto() != null) {
            String imageurl = goodsListItem.getPhoto();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        } else {
            icon.setImageResource(R.mipmap.default_img);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        TextView tv4 = findViewById(R.id.tv_header4);
        TextView tv5 = findViewById(R.id.tv_header5);
        tv1.setText(goodsListItem.getGoodsNameNo());
        tv2.setText(goodsListItem.getAttr1());
        tv3.setText("备注:" + Utils.nullOrEmpty(goodsListItem.getDescription()));
        tv4.setText(goodsListItem.getNumber());
        tv5.setText(goodsListItem.getAttr2());
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTablayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPager);

        mTabAdapter = new GoodsDetailTapAdapter(getSupportFragmentManager(),goodsListItem.getId());
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

    private void getData() {
        compositeSubscription.add(dataManager.getGoodsDetail(SpHelper.getInstance(mContext).getEnterpriseId(), goodsListItem.getId()).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast(mContext.getString(R.string.toast_loading_error));
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666", s);
                                Gson gson = new Gson();
                                GoodsDetailBean bean = gson.fromJson(s, GoodsDetailBean.class);
                                GoodsDetailBean.GoodsDetailObj goodsDetailObj = bean.getObj();

                                Map<String, JSONArray> attrList = Utils.getMapForJson(bean.getObj().getAttrList());
                                List<String> attrSList = new ArrayList<>();
                                if (attrList != null) {
                                    for (Map.Entry<String, JSONArray> entry : attrList.entrySet()) {
                                        //获取key
                                        String key = entry.getKey();
                                        //获取value
                                        JSONArray value = entry.getValue();
                                        JSONObject valueMap = null;
                                        try {
                                            valueMap = value.getJSONObject(0);
                                        } catch (Exception e) {
                                        }
                                        if (valueMap != null) {
                                            try {
                                                if (!StringUtils.isNullOrEmpty(key)) {
                                                    attrSList.add(key + ": " + valueMap.getString("name"));
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                Log.e("net777", String.valueOf(attrSList));
                                List<String> specList = new ArrayList<>();
                                if (!StringUtils.isNullOrEmpty(bean.getObj().getX())) {
                                    specList.add(goodsDetailObj.getX());
                                }
                                if (!StringUtils.isNullOrEmpty(bean.getObj().getY())) {
                                    specList.add(goodsDetailObj.getY());
                                }
                                List<GoodsDetailBean.GoodsSpecItem> specItems = goodsDetailObj.getGoodsSpecList();
                                List<String> specSLsit = new ArrayList<>();
                                if (specItems == null) {
                                    specItems = new ArrayList<>();
                                }
                                if (specList.size() == 1) {
                                    String x = goodsDetailObj.getX() + ": ";
                                    for (int n = 0; n < specItems.size(); n++) {
                                        GoodsDetailBean.GoodsSpecItem item = specItems.get(n);
                                        if (n != specItems.size() - 1) {
                                            x = x + item.getX() + " , ";
                                        } else {
                                            x = x + item.getX();
                                        }
                                    }
                                    specSLsit.add(x);

                                } else if (specList.size() == 2) {
                                    String x = goodsDetailObj.getX() + ": ";
                                    String y = goodsDetailObj.getY() + ": ";
                                    List<String> xList = new ArrayList<>();
                                    List<String> yList = new ArrayList<>();
                                    for (int n = 0; n < specItems.size(); n++) {
                                        GoodsDetailBean.GoodsSpecItem item = specItems.get(n);
                                        if (!xList.contains(item.getX())) {
                                            xList.add(item.getX());
                                        }

                                        if (!yList.contains(item.getY())) {
                                            yList.add(item.getY());
                                        }

                                    }
                                    for (int n = 0; n < xList.size(); n++) {
                                        String item = xList.get(n);
                                        if (n != xList.size() - 1) {
                                            x = x + item + " , ";
                                        } else {
                                            x = x + item;
                                        }
                                    }

                                    for (int n = 0; n < yList.size(); n++) {
                                        String item = yList.get(n);
                                        if (n != yList.size() - 1) {
                                            y = y + item + " , ";
                                        } else {
                                            y = y + item;
                                        }
                                    }
                                    specSLsit.add(x);
                                    specSLsit.add(y);
                                }
                                if (attrSList.size() == 0){
                                    attrSList.add(mContext.getString(R.string.goods_null));
                                }
                                if (specSLsit.size() == 0){
                                    specSLsit.add(mContext.getString(R.string.goods_null));
                                }
                                mTabAdapter.setData(attrSList, specSLsit);
                                mTabAdapter.setRemark(bean.getObj().getDescription());
                            }
                        })
        );
    }
}
