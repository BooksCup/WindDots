package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
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
 * 面料盘点任务处理(按卷盘点)
 *
 * @author zhou
 */
public class FabricCheckLotProcessActivity extends BaseActivity {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;

    private FabricCheckTaskLotProcessAdapter mAdapter;

    private List<FabricCheckTaskLot> mDataSource = new ArrayList<>();

    private String mId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_task_lot);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        initView();
        getData();

    }

    private void initView(){
        Intent intent = getIntent();
        mId = intent.getStringExtra("data");
        mAdapter = new FabricCheckTaskLotProcessAdapter(R.layout.item_fabric_check_task_lot_process,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back,R.id.ll_save})
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


    private void getData(){
        String url = Constant.APP_BASE_URL + "fabricCheckRecord/searchAll?checkLotInfoId=" + mId;
        mVolleyUtil.httpGetRequest(url, response -> {
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

    private void onSaveDidClick(){

        List<FabricCheckTaskLot> list = mAdapter.getData();
        List<Map> paramsMaps = new ArrayList<>();
        for (int i = 0;i < list.size();i++){
            FabricCheckTaskLot lotItem = list.get(i);
            List<FabricCheckTaskRecord> recordList = lotItem.getFabricCheckRecords();
            if (recordList == null || recordList.size() == 0){
                continue;
            }
            if (StringUtils.isNullOrEmpty(lotItem.getDeliveryDate())){
                Toast.makeText(this,"请先选择日期",Toast.LENGTH_LONG).show();
                return;
            }
            for (int m = 0;m < recordList.size();m++){
                FabricCheckTaskRecord fabricCheckTaskRecord = recordList.get(m);
                Map<String,String> map = new HashMap<>();
                map.put("deliveryDate",lotItem.getDeliveryDate());
                map.put("sno",m + "");
                map.put("weightBefore", Utils.nullOrEmpty(fabricCheckTaskRecord.getWeightBefore()));
                map.put("lengthBefore",Utils.nullOrEmpty(fabricCheckTaskRecord.getLengthBefore()));
                map.put("checkLotInfoId",mId);
                paramsMaps.add(map);
            }
        }

        String url = Constant.APP_BASE_URL + "fabricCheckRecord";
        String paramsJson = JSON.toJSONString(paramsMaps);
        Log.e("net666",paramsJson);
        Map<String,String> params = new HashMap<>();
        params.put("fabricCheckRecords",paramsJson);
        mVolleyUtil.httpPostRequest(url, params, response -> {
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
            Log.e("net666", String.valueOf(volleyError));
        });

    }
}
