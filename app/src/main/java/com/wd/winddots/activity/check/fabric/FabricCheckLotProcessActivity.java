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
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotProcessAdapter;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 面料盘点任务处理(按卷盘点)
 *
 * @author zhou
 */
public class FabricCheckLotProcessActivity extends BaseActivity implements FabricCheckTaskLotProcessAdapter.SubItemDidClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    private FabricCheckTaskLotProcessAdapter mAdapter;

    private List<FabricCheckTaskLot> mDataSource = new ArrayList<>();

    private String mId;
    private String mGoodsName;
    private String mGoodsNo;
    private String mStatus;
    private String mFabricCheckTaskId;

    private List<String> mProblemStringList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_task_lot_process);
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
        mAdapter = new FabricCheckTaskLotProcessAdapter(R.layout.item_fabric_check_task_lot_process, mDataSource);
        mAdapter.setSubItemDidClickListener(this);
        mAdapter.mCheckLotInfoId = mId;
        mAdapter.mFabricCheckTaskId = mFabricCheckTaskId;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_save, R.id.tv_finish,R.id.tv_browse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                onSaveDidClick(true);
                break;
            case R.id.tv_finish:
                onFinishDidClick();
                break;
            case R.id.tv_browse:
                onBrowseClick();
                break;
        }
    }


    private void getData() {
        String url = Constant.APP_BASE_URL + "fabricCheckRecord/searchAll?checkLotInfoId=" + mId;
        Log.e("net666",url);
        mVolleyUtil.httpGetRequest(url, response -> {
            if (null == response) {
                return;
            }
            Log.e("net666", response);
            FabricCheckLot fabricCheckLot = JSON.parseObject(response,FabricCheckLot.class);
            List<FabricCheckTaskLot> list = fabricCheckLot.getFabricQcRecordAllByCheckLIIdVoList();
            mDataSource.clear();
            mDataSource.addAll(list);
            mAdapter.notifyDataSetChanged();

            List<ProblemImage> problemImageList = fabricCheckLot.getProblemImageClassifyList();
            if (problemImageList == null || problemImageList.size() == 0){
                return;
            }
            mProblemStringList.clear();
            for (int i = 0;i < problemImageList.size();i++){
                mProblemStringList.add(problemImageList.get(i).getTag());
            }

        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    private void onSaveDidClick(boolean showToast) {

        if ("1".equals(mStatus)){
            showToast("该盘点已完成,不能再次更改");
            return;
        }

        List<FabricCheckTaskLot> list = mAdapter.getData();
        List<Map> paramsMaps = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FabricCheckTaskLot lotItem = list.get(i);
            List<FabricCheckTaskRecord> recordList = lotItem.getFabricCheckRecords();
            for (int m = 0; m < recordList.size(); m++) {
                FabricCheckTaskRecord fabricCheckTaskRecord = recordList.get(m);
                Map<String, String> map = new HashMap<>();
                map.put("weightAfter", Utils.nullOrEmpty(fabricCheckTaskRecord.getWeightAfter()));
                map.put("lengthAfter", Utils.nullOrEmpty(fabricCheckTaskRecord.getLengthAfter()));
                map.put("id", fabricCheckTaskRecord.getId());
                map.put("modifyTime","modifyTimeExamine");
                map.put("fabricCheckTaskId",mFabricCheckTaskId);
                map.put("machineNumber",Utils.nullOrEmpty(lotItem.getMachineNumber()));
                map.put("palletNumber",Utils.nullOrEmpty(lotItem.getPalletNumber()));
                paramsMaps.add(map);
            }
        }


        String url = Constant.APP_BASE_URL + "fabricCheckRecord?&modifyTime=modifyTimeExamine&fabricCheckTaskId=" + mFabricCheckTaskId;
        Log.e("net666",url);
        String paramsJson = JSON.toJSONString(paramsMaps);
        Log.e("net666", paramsJson);
        Map<String, String> params = new HashMap<>();
        params.put("fabricCheckRecords", paramsJson);
        mVolleyUtil.httpPostRequest(url, params, response -> {
           if (showToast){
               Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
           }
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
            Log.e("net666", String.valueOf(volleyError));
        });

    }
    
    private void onFinishDidClick(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要将该盘点置为已完成吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = Constant.APP_BASE_URL + "fabricCheckLotInfo/" + mId;

                Map<String,String> params = new HashMap<>();
                params.put("status","1");
                params.put("fabricCheckTaskId",mFabricCheckTaskId);
                Log.e("net666",JSON.toJSONString(params));

                mVolleyUtil.httpPutRequest(url, params, response -> {
                    showToast("操作成功");
                    finish();
                }, volleyError -> {
                    mVolleyUtil.handleCommonErrorResponse(FabricCheckLotProcessActivity.this, volleyError);
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


    private void onBrowseClick(){
        Intent intent = new Intent(this, FabricCheckLotBrowseActivity.class);
        intent.putExtra("data", mId);
        intent.putExtra("goodsName",mGoodsName);
        intent.putExtra("goodsNo", mGoodsNo);
        intent.putExtra("status", mStatus);
        intent.putExtra("fabricCheckTaskId", mFabricCheckTaskId);
        startActivityForResult(intent,100);
    }


    @Override
    public void onSubItemDidClickListener(int position, FabricCheckTaskRecord subItem) {

        onSaveDidClick(false);
        Intent intent = new Intent(this, FabricCheckProblemActivity.class);
        intent.putExtra("recordId", subItem.getId());
        intent.putExtra("goodsName", mGoodsName);
        intent.putExtra("goodsNo", mGoodsNo);
        intent.putExtra("date",mDataSource.get(position).getDeliveryDate());
        intent.putExtra("position",(position + 1) + "");
        intent.putExtra("problemString",JSON.toJSONString(mProblemStringList));
        startActivityForResult(intent,10001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            getData();
        }
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
