package com.wd.winddots.desktop.list.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.adapter.CheckDetailAdapter;
import com.wd.winddots.desktop.list.check.bean.CheckDetailBean;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

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
 * FileName: CheckDetailActivity
 * Author: 郑
 * Date: 2021/1/15 11:05 AM
 * Description:
 */
public class CheckDetailActivity extends FragmentActivity {


    private final static int REQUEST_CODE_ADD_QUESTION = 101;

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check_detail)
    RecyclerView mCheckDetailRv;

    private TextView mBeforeLengthTv;
    private TextView mBeforeWeightTv;

    private EditText mAfterLengthEt;
    private EditText mAfterWeightEt;

    private EditText mRemarkEt;

    private LoadingDialog mDialog;


    private CheckDetailAdapter mAdapter;
    private List<CheckDetailBean.CheckDetailItem> mDataSource = new ArrayList<>();

    //recordId
    String mRecordId = "";
    private String mCylinderNumber = "";
    private String mGoodsName = "";
    private String mGoodsNo = "";
    private String mSno = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_detail);

        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        mDialog = LoadingDialog.getInstance(this);

        Intent intent = getIntent();
        mRecordId = intent.getStringExtra("data");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mCylinderNumber = intent.getStringExtra("cylinderNumber");
        mSno = intent.getStringExtra("sno");

        initView();
        getData();
    }


    private void initView(){
        mAdapter = new CheckDetailAdapter(R.layout.item_check_detail,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckDetailRv.setLayoutManager(layoutManager);
        mCheckDetailRv.setAdapter(mAdapter);
        View headerView = View.inflate(this, R.layout.view_check_detail_header, null);
        TextView goodsTv = headerView.findViewById(R.id.tv_goods);
        TextView snoTv = headerView.findViewById(R.id.tv_sno);
        TextView numberTv = headerView.findViewById(R.id.tv_number);
        String title = Utils.nullOrEmpty(mGoodsName) + "(" + Utils.nullOrEmpty(mGoodsNo) + ")";
        goodsTv.setText(title);
        snoTv.setText(Utils.nullOrEmpty(mSno));
        numberTv.setText(Utils.nullOrEmpty(mCylinderNumber));
        mBeforeLengthTv = headerView.findViewById(R.id.tv_before_length);
        mBeforeWeightTv = headerView.findViewById(R.id.tv_before_weight);
        mAfterLengthEt = headerView.findViewById(R.id.et_after_length);
        mAfterWeightEt = headerView.findViewById(R.id.et_after_weight);
        View footerView = View.inflate(this, R.layout.view_check_detail_footer, null);
        mRemarkEt = footerView.findViewById(R.id.et_footer);
        mAdapter.setHeaderView(headerView);
        mAdapter.setFooterView(footerView);
    }

    @OnClick({R.id.iv_back,R.id.ll_add_question,R.id.ll_save})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_add_question:
                onAddQuestionDidClick();
                break;
            case R.id.ll_save:
                onSaveDidClick();
                break;
        }
    }


    /*
    * 获取数据
    * */
    private void getData(){
        String url = Constant.APP_BASE_URL + "fabricQcRecord/" + mRecordId;
        mVolleyUtil.httpGetRequest(url, response -> {
            CheckDetailBean data = JSON.parseObject(response, CheckDetailBean.class);
            mAdapter.setNewData(data.getRecordProblemList());
            String beforeLenght = "盘点前(米):" + Utils.nullOrEmpty(data.getLengthBefore());
            String beforeWeight = "盘点前(公斤): " + Utils.nullOrEmpty(data.getWeightBefore());
            mBeforeLengthTv.setText(beforeLenght);
            mBeforeWeightTv.setText(beforeWeight);
            mAfterLengthEt.setText(Utils.nullOrEmpty(data.getLengthAfter()));
            mAfterWeightEt.setText(Utils.nullOrEmpty(data.getWeightAfter()));
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }



    /*
    * 点击添加问题按钮
    * */
    private void onAddQuestionDidClick(){
        Intent intent = new Intent(this,CheckQuestionActivity.class);
        intent.putExtra("data",mRecordId);
        intent.putExtra("goodsName",Utils.nullOrEmpty(mGoodsName));
        intent.putExtra("goodsNo", Utils.nullOrEmpty(mGoodsNo));
        intent.putExtra("sno",Utils.nullOrEmpty(mSno));
        intent.putExtra("cylinderNumber",Utils.nullOrEmpty(mCylinderNumber));
        startActivityForResult(intent,REQUEST_CODE_ADD_QUESTION);
    }

    /*
    * 点击保存按钮
    * */
    private void onSaveDidClick(){
        String length = mAfterLengthEt.getText().toString().trim();
        if (StringUtils.isNullOrEmpty(length)){
            Toast.makeText(this,getText(R.string.check_detail_length_empty),Toast.LENGTH_LONG).show();
            return;
        }
        String weight = mAfterWeightEt.getText().toString().trim();
        if (StringUtils.isNullOrEmpty(weight)){
            Toast.makeText(this,getText(R.string.check_detail_weight_empty),Toast.LENGTH_LONG).show();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("id",mRecordId);
        params.put("lengthAfter",length);
        params.put("weightAfter",weight);
        String remark = mRemarkEt.getText().toString().trim();
        if (!StringUtils.isNullOrEmpty(remark)){
            params.put("remark",remark);
        }
        mDialog.show();
        String url = Constant.APP_BASE_URL + "fabricQcRecord/updateById";
        mVolleyUtil.httpPutRequest(url,params,response -> {
            mDialog.hide();
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }, volleyError->{
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
