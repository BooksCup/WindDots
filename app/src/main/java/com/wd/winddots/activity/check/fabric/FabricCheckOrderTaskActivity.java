package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.adapter.check.fabric.CheckInfoAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.BottomSearchBarView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * FileName: FabricCheckOrderTaskActivity
 * Author: 郑
 * Date: 2021/3/8 3:24 PM
 * Description:
 */
public class FabricCheckOrderTaskActivity extends FragmentActivity implements BottomSearchBarView.BottomSearchBarViewClickListener {


    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private CheckInfoAdapter mAdapter;
    private List<FabricCheckLotInfo> mDataSource = new ArrayList<>();

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.sdv_goods_photo)
    ImageView mGoodsIv;

    @BindView(R.id.tv_goods_info)
    TextView mGoodsInfoTv;

    @BindView(R.id.tv_related_company)
    TextView mRCompanyTv;

    @BindView(R.id.tv_date)
    TextView mDateTv;

    @BindView(R.id.tv_order_no)
    TextView mOrderNoTv;

    @BindView(R.id.tv_theme)
    TextView mThemeTv;

    @BindView(R.id.bottomBar)
    BottomSearchBarView mBottomSearchBarView;


    private FabricCheckTask mFabricCheckTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_order_task);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        getData();
    }
    
    private void initView(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        mFabricCheckTask = JSON.parseObject(data,FabricCheckTask.class);
        String goodsInfo = mFabricCheckTask.getGoodsName() + "(" + mFabricCheckTask.getGoodsNo() + ")";
        mGoodsInfoTv.setText(goodsInfo);
        mOrderNoTv.setText("#" + mFabricCheckTask.getOrderNo());
        mThemeTv.setText(mFabricCheckTask.getOrderTheme());
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(mFabricCheckTask.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            GlideApp.with(this).load(goodsPhoto + Utils.OSSImageSize(200)).into(mGoodsIv);
        } else {
            mGoodsIv.setImageResource(R.mipmap.icon_default_goods);
        }

        //mDataSource.addAll(mFabricCheckTask.getFabricCheckLotInfoList());
        mAdapter = new CheckInfoAdapter(R.layout.item_check_info, mDataSource);
        mAdapter.fabricCheckTaskId = mFabricCheckTask.getId();
        mAdapter.goodsName = mFabricCheckTask.getGoodsName();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);

        mBottomSearchBarView.setOnIconClickListener(this);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getData(){
        String url = Constant.APP_BASE_URL + "fabricCheckLotInfo/getLotNoList/" + mFabricCheckTask.getId();

        Log.e("net666", url);

        mVolleyUtil.httpGetRequest(url, response -> {
            if (null == response) {
                return;
            }
            List<FabricCheckLotInfo> lotInfoList = JSON.parseArray(response,FabricCheckLotInfo.class);
            mAdapter.setNewData(lotInfoList);
        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    public void onAddIconDidClick() {
        FabricCheckLotInfo fabricCheckLotInfo = new FabricCheckLotInfo(true);
        mAdapter.addData(fabricCheckLotInfo);
    }

    @Override
    public void onSearchIconDidClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }
}
