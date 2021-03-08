package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotBrowseAdapter;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotProcessAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckLotBrowseActivity
 * Author: 郑
 * Date: 2021/3/1 11:39 AM
 * Description:
 */
public class FabricCheckLotBrowseActivity extends BaseActivity {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    private FabricCheckTaskLotBrowseAdapter mAdapter;

    private List<FabricCheckTaskLot> mDataSource = new ArrayList<>();

    private String mId;
    private String mGoodsName;
    private String mGoodsNo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_lot_browse);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        initView();
        getData();

    }

    private void initView(){
        Intent intent = getIntent();
        mId = intent.getStringExtra("data");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mTitleTv.setText(Utils.nullOrEmpty(mGoodsNo) + "(" + Utils.nullOrEmpty(mGoodsName) + ")");
        mAdapter = new FabricCheckTaskLotBrowseAdapter(R.layout.item_fabric_check_task_lot_browse,mDataSource);
        mAdapter.goodsName = mGoodsName;
        mAdapter.goodsNo = mGoodsNo;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
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
        String url = Constant.APP_BASE_URL + "fabricCheckRecord/searchAll?checkLotInfoId=" + mId;
        mVolleyUtil.httpGetRequest(url, response -> {
            Log.e("net666",response);
            if (null == response){
                return;
            }
            List<FabricCheckTaskLot> list = JSON.parseArray(response,FabricCheckTaskLot.class);
            mDataSource.addAll(list);
            mAdapter.notifyDataSetChanged();
        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }



}
