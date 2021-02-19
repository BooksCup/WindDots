package com.wd.winddots.desktop.list.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.adapter.CheckAdapter;
import com.wd.winddots.desktop.list.check.bean.CheckBean;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: CheckActivity
 * Author: 郑
 * Date: 2021/1/15 10:13 AM
 * Description: 选择缸号盘点
 */
public class CheckActivity extends FragmentActivity implements BaseQuickAdapter.OnItemClickListener, CheckAdapter.CheckItemTextChangeListener {


    private final static int REQUEST_CODE_CHECK_DETAIL = 10086;

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_length)
    TextView mLengthTv;

    @BindView(R.id.tv_weight)
    TextView mWeigthTv;


    private String mWarehouseId = "";
    private String mCylinderNumber = "";
    private String mGoodsName = "";
    private String mGoodsNo = "";
    private String mOddTime = "";

    private CheckAdapter mAdapter;

    private List<CheckBean.FabricQcRecord> mDataSource = new ArrayList<>();
    private CheckBean mData;

    private LoadingDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        mDialog = LoadingDialog.getInstance(this);

        Intent intent = getIntent();
        mWarehouseId = intent.getStringExtra("warehouseId");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mCylinderNumber = intent.getStringExtra("cylinderNumber");
        mOddTime = intent.getStringExtra("oddTime");

        initView();
        getData();
    }


    private void initView() {

        String title = Utils.nullOrEmpty(mGoodsName) + "  ";
        if (mOddTime.length() >= 10) {
            title = title + " " + mOddTime.substring(5, 10);
        }
        title = title + " " + Utils.nullOrEmpty(mCylinderNumber);
        mTitleTv.setText(title);

        mAdapter = new CheckAdapter(R.layout.item_check, mDataSource);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setTextChangeListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
    }

    /*
     * 获取数据
     * */
    private void getData() {
        String url = Constant.APP_BASE_URL + "fabricQcRecord/getByWarehouseId?warehouseId=" + mWarehouseId;
        Log.e("net666", url);
        mVolleyUtil.httpGetRequest(url, response -> {
            Log.e("net666", response);
            CheckBean data = JSON.parseObject(response, CheckBean.class);
            mAdapter.setNewData(data.getFabricQcRecordList());
            String lenght = "盘点(米):" + Utils.nullOrEmpty(data.getLengthBeforeTotal()) + "/" + Utils.nullOrEmpty(data.getLengthAfterTotal());
            String weight = "盘点(公斤): " + Utils.nullOrEmpty(data.getWeightBeforeTotal()) + "/" + Utils.nullOrEmpty(data.getWeightAfterTotal());
            mLengthTv.setText(lenght);
            mWeigthTv.setText(weight);
            mData = data;
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @OnClick({R.id.iv_back, R.id.ll_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_save:
                onSaveDidClick();
                break;
        }
    }

    /*
     * item 点击事件
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, CheckDetailActivity.class);
        intent.putExtra("data", mAdapter.getData().get(position).getId());
        intent.putExtra("goodsName", Utils.nullOrEmpty(mGoodsName));
        intent.putExtra("goodsNo", Utils.nullOrEmpty(mGoodsNo));
        intent.putExtra("sno", Utils.nullOrEmpty(mAdapter.getData().get(position).getSno()));
        intent.putExtra("cylinderNumber", Utils.nullOrEmpty(mCylinderNumber));
        startActivityForResult(intent, REQUEST_CODE_CHECK_DETAIL);
    }

    /*
     * 盘点后米数变化
     * */
    @Override
    public void afterLengthChange() {
        List<CheckBean.FabricQcRecord> list = mAdapter.getData();
        float total = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (int i = 0; i < list.size(); i++) {
            CheckBean.FabricQcRecord fabricQcRecord = list.get(i);
            nf.setMaximumFractionDigits(2);
            float length = Float.parseFloat(Utils.numberNullOrEmpty(fabricQcRecord.getLengthAfter()));
            total = length + total;
        }
        String amountS = String.valueOf(nf.format(total));
        String lenght = "盘点(米):" + Utils.nullOrEmpty(mData.getLengthBeforeTotal()) + "/" + amountS;
        mLengthTv.setText(lenght);
    }

    /*
     * 盘点后公斤数变化
     * */
    @Override
    public void afterWeightChange() {
        List<CheckBean.FabricQcRecord> list = mAdapter.getData();
        float total = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (int i = 0; i < list.size(); i++) {
            CheckBean.FabricQcRecord fabricQcRecord = list.get(i);
            nf.setMaximumFractionDigits(2);
            float weight = Float.parseFloat(Utils.numberNullOrEmpty(fabricQcRecord.getWeightAfter()));
            total = weight + total;
        }
        String amountS = nf.format(total);
        String weight = "盘点(公斤): " + Utils.nullOrEmpty(mData.getWeightBeforeTotal()) + "/" + Utils.nullOrEmpty(amountS);
        mWeigthTv.setText(weight);
    }

    /*
     * 点击保存
     * */
    private void onSaveDidClick() {

        List<Map> paramsMap = new ArrayList<>();
        List<CheckBean.FabricQcRecord> list = mAdapter.getData();

        float totalLength = 0;
        float totalWeight = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        for (int i = 0; i < list.size(); i++) {
            CheckBean.FabricQcRecord fabricQcRecord = list.get(i);
            Map<String, String> map = new HashMap<>();
            if (!StringUtils.isNullOrEmpty(fabricQcRecord.getWeightAfter())) {
                map.put("weightAfter", fabricQcRecord.getWeightAfter());
            }
            if (!StringUtils.isNullOrEmpty(fabricQcRecord.getLengthAfter())) {
                map.put("lengthAfter", fabricQcRecord.getLengthAfter());
            }
            if (!StringUtils.isNullOrEmpty(fabricQcRecord.getWeightAfter()) || !StringUtils.isNullOrEmpty(fabricQcRecord.getLengthAfter())) {
                map.put("id", fabricQcRecord.getId());
                paramsMap.add(map);
            }
            float weight = Float.parseFloat(Utils.numberNullOrEmpty(fabricQcRecord.getWeightAfter()));
            totalWeight = weight + totalWeight;
            float length = Float.parseFloat(Utils.numberNullOrEmpty(fabricQcRecord.getLengthAfter()));
            totalLength = length + totalLength;
        }

        if (totalLength == 0 && totalWeight == 0) {
            Toast.makeText(this, "请先输入数量", Toast.LENGTH_LONG).show();
            return;
        }

        String url = Constant.APP_BASE_URL + "fabricQcRecord/updateByIds";
        Map<String, String> params = new HashMap<>();
        params.put("string", JSON.toJSONString(paramsMap));
        Log.e("net666", JSON.toJSONString(paramsMap));
        mDialog.show();
        mVolleyUtil.httpPostRequest(url, params, response -> {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
            getData();
            mDialog.hide();
        }, volleyError -> {
            mDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }
}
