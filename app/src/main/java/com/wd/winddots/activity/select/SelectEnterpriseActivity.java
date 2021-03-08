package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.select.EnterpriseAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Enterprise;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 企业选择
 *
 * @author zhou
 */
public class SelectEnterpriseActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = "SelectEnterprise";

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.rv_enterprise)
    RecyclerView mEnterpriseRv;

    private EnterpriseAdapter mAdapter;

    private List<Enterprise> mEnterpriseList = new ArrayList<>();

    private VolleyUtil mVolleyUtil;
    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_enterprise);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        mLoadingDialog = LoadingDialog.getInstance(this);
        initView();
    }

    public void initView() {
        mAdapter = new EnterpriseAdapter(R.layout.item_select_enterprise, mEnterpriseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mEnterpriseRv.setLayoutManager(layoutManager);
        mEnterpriseRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mEnterpriseRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        String keyword = getIntent().getStringExtra("keyword");
        if (!TextUtils.isEmpty(keyword)) {
            getEnterpriseListByKeyword(keyword);
            mSearchEt.setText(keyword);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                mLoadingDialog.show();
                String keyword = mSearchEt.getText().toString();
                getEnterpriseListByKeyword(keyword);
                break;
        }
    }

    /**
     * 根据关键字搜索企业
     *
     * @param keyword 昵称
     */
    private void getEnterpriseListByKeyword(String keyword) {
        String url;
        try {
            url = Constant.APP_BASE_URL + "enterprise/search?keyword=" + URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "enterprise/search?keyword=" + keyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            mLoadingDialog.hide();
            Log.d(TAG, "server response: " + response);
            List<Enterprise> enterpriseList = JSON.parseArray(response, Enterprise.class);
            mEnterpriseList.clear();
            mEnterpriseList.addAll(enterpriseList);
            mAdapter.setKeyword(keyword);
            mAdapter.notifyDataSetChanged();

        }, volleyError -> {
            mLoadingDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(SelectEnterpriseActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String keyword = mSearchEt.getText().toString();
        Enterprise enterprise = mEnterpriseList.get(position);
        enterprise.setKeyword(keyword);
        String json = JSON.toJSONString(enterprise);
        Intent intent = new Intent();
        intent.putExtra("data", json);
        setResult(250, intent);
        finish();
    }
}
