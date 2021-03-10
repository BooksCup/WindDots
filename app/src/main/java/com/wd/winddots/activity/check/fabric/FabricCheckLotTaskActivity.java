package com.wd.winddots.activity.check.fabric;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.entity.ProblemImage;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

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
 * FileName: FabricCheckTaskNumberActivity
 * Author: 郑
 * Date: 2021/2/23 11:39 AM
 * Description:
 */
public class FabricCheckLotTaskActivity extends FragmentActivity {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    private FabricCheckTaskLotAdapter mAdapter;

    private List<FabricCheckTaskLot> mDataSource = new ArrayList<>();

    private String mId;
    private String mGoodsName;
    private String mGoodsNo;
    private String mStatus;
    private String mFabricCheckTaskId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_task_lot);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        initView();
        getData();

    }

    private void initView() {
        Intent intent = getIntent();
        mId = intent.getStringExtra("data");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mStatus = intent.getStringExtra("status");
        mFabricCheckTaskId = intent.getStringExtra("fabricCheckTaskId");
        mTitleTv.setText(Utils.nullOrEmpty(mGoodsNo) + "(" + Utils.nullOrEmpty(mGoodsName) + ")");
        mAdapter = new FabricCheckTaskLotAdapter(R.layout.item_fabric_check_task_number, mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.ll_save,R.id.tv_delete,R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_save:
                onSaveDidClick();
                break;
            case R.id.tv_delete:
                onDeleteDidClick();
                break;
            case R.id.tv_add:
                onAddDidClick();
                break;
        }
    }


    private void getData() {
        String url = Constant.APP_BASE_URL + "fabricCheckRecord/searchAll?checkLotInfoId=" + mId;

        Log.e("net666", url);

        mVolleyUtil.httpGetRequest(url, response -> {

            if (null == response) {
                return;
            }
            FabricCheckLot fabricCheckLot = JSON.parseObject(response,FabricCheckLot.class);
            List<FabricCheckTaskLot> list = fabricCheckLot.getFabricQcRecordAllByCheckLIIdVoList();
            if (list == null ||  list.size() == 0) {
                list = new ArrayList<>();
                list.add(new FabricCheckTaskLot());
            }
            mDataSource.addAll(list);
            mAdapter.notifyDataSetChanged();
        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    private void onSaveDidClick() {

        if ("1".equals(mStatus)){
            Toast.makeText(this, "该盘点已完成,不能再次更改", Toast.LENGTH_LONG).show();
            return;
        }

        List<FabricCheckTaskLot> list = mAdapter.getData();
        List<Map> paramsMaps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FabricCheckTaskLot lotItem = list.get(i);
            List<FabricCheckTaskRecord> recordList = lotItem.getFabricCheckRecords();
            if (recordList == null || recordList.size() == 0) {
                continue;
            }
            if (StringUtils.isNullOrEmpty(lotItem.getDeliveryDate())) {
                Toast.makeText(this, "请先选择日期", Toast.LENGTH_LONG).show();
                return;
            }
            for (int m = 0; m < recordList.size(); m++) {
                FabricCheckTaskRecord fabricCheckTaskRecord = recordList.get(m);
                Map<String, String> map = new HashMap<>();
                map.put("deliveryDate", lotItem.getDeliveryDate());
                map.put("sno", m + "");
                map.put("weightBefore", Utils.nullOrEmpty(fabricCheckTaskRecord.getWeightBefore()));
                map.put("lengthBefore", Utils.nullOrEmpty(fabricCheckTaskRecord.getLengthBefore()));
                map.put("checkLotInfoId", mId);
                map.put("modifyTime","modifyTimeApply");
                map.put("fabricCheckTaskId",mFabricCheckTaskId);
                if (!StringUtils.isNullOrEmpty(fabricCheckTaskRecord.getId())) {
                    map.put("id", fabricCheckTaskRecord.getId());
                }
                paramsMaps.add(map);
            }
        }
        String url = Constant.APP_BASE_URL + "fabricCheckRecord?&modifyTime=modifyTimeApply&fabricCheckTaskId=" + mFabricCheckTaskId;
        String paramsJson = JSON.toJSONString(paramsMaps);
        Log.e("net666", paramsJson);
        Map<String, String> params = new HashMap<>();
        params.put("fabricCheckRecords", paramsJson);
        mVolleyUtil.httpPostRequest(url, params, response -> {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
            finish();
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
            Log.e("net666", String.valueOf(volleyError));
        });

    }

    /*
    * 点击删除
    * */
    private void onDeleteDidClick(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要删除该盘点吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = Constant.APP_BASE_URL + "fabricCheckLotInfo/" + mId;
                Map<String,String> params = new HashMap<>();
                params.put("isDelete","1");
                mVolleyUtil.httpPutRequest(url, params, response -> {
                    Toast.makeText(FabricCheckLotTaskActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    finish();
                }, volleyError -> {
                    mVolleyUtil.handleCommonErrorResponse(FabricCheckLotTaskActivity.this, volleyError);
                });
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }


    /*
    * 点击添加日期
    * */
    private void onAddDidClick(){
        mAdapter.addData(new FabricCheckTaskLot());
    }


    private static class FabricCheckLot{
        private List<FabricCheckTaskLot> fabricQcRecordAllByCheckLIIdVoList;
        private List<ProblemImage> problemImageClassifyList;

        public List<FabricCheckTaskLot> getFabricQcRecordAllByCheckLIIdVoList() {
            return fabricQcRecordAllByCheckLIIdVoList;
        }

        public void setFabricQcRecordAllByCheckLIIdVoList(List<FabricCheckTaskLot> fabricQcRecordAllByCheckLIIdVoList) {
            this.fabricQcRecordAllByCheckLIIdVoList = fabricQcRecordAllByCheckLIIdVoList;
        }

        public List<ProblemImage> getProblemImageClassifyList() {
            return problemImageClassifyList;
        }

        public void setProblemImageClassifyList(List<ProblemImage> problemImageClassifyList) {
            this.problemImageClassifyList = problemImageClassifyList;
        }
    }

}
