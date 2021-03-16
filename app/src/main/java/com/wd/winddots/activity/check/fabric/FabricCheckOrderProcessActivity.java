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
import com.wd.winddots.adapter.check.fabric.CheckTaskInfoAdapter;
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
public class FabricCheckOrderProcessActivity extends FragmentActivity implements CheckTaskInfoAdapter.OnSubItemDidClickListener {


    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private CheckTaskInfoAdapter mAdapter;
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
        String goodsInfo = Utils.nullOrEmpty(mFabricCheckTask.getGoodsName()) + "(" + Utils.nullOrEmpty(mFabricCheckTask.getGoodsNo()) + ")";
        mGoodsInfoTv.setText(goodsInfo);
        mOrderNoTv.setText("#" + Utils.nullOrEmpty(mFabricCheckTask.getOrderNo()));
        mThemeTv.setText(Utils.nullOrEmpty(mFabricCheckTask.getOrderTheme()));
        mRCompanyTv.setText(Utils.nullOrEmpty(mFabricCheckTask.getRelatedCompanyShortName()));
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(mFabricCheckTask.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            GlideApp.with(this).load(goodsPhoto + Utils.OSSImageSize(200)).into(mGoodsIv);
        } else {
            mGoodsIv.setImageResource(R.mipmap.icon_default_goods);
        }

        //mDataSource.addAll(mFabricCheckTask.getFabricCheckLotInfoList());
        mAdapter = new CheckTaskInfoAdapter(R.layout.item_check_info, mDataSource);
        mAdapter.fabricCheckTaskId = mFabricCheckTask.getId();
        mAdapter.goodsName = mFabricCheckTask.getGoodsName();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
        mBottomSearchBarView.setVisibility(View.GONE);
        mAdapter.setOnSubItemDidClickListener(this);
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
    public void onSubItemDidClick(int position) {
        FabricCheckLotInfo item = mAdapter.getData().get(position);
        Intent intent;
        if ("1".equals(item.getStatus())) {
            intent = new Intent(this, FabricCheckLotBrowseActivity.class);
        } else {
            intent = new Intent(this, FabricCheckLotProcessActivity.class);
        }
        intent.putExtra("data", item.getId());
        intent.putExtra("goodsName", mFabricCheckTask.getGoodsName());
        intent.putExtra("goodsNo", item.getLotNo());
        intent.putExtra("status", item.getStatus());
        intent.putExtra("fabricCheckTaskId", item.getFabricCheckTaskId());
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }
}
