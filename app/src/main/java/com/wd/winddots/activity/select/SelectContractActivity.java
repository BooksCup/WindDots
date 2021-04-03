package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.contract.SignContractAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Contract;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择合同
 *
 * @author zhou
 */
public class SelectContractActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    static String TAG = "SignContractActivity";
    @BindView(R.id.rv_contract)
    RecyclerView mContractRv;

    @BindView(R.id.srl_contract)
    SwipeRefreshLayout mContractSrl;

    User mUser;
    int mPage = 1;
    int mPageSize = 10;
    VolleyUtil mVolleyUtil;
    SignContractAdapter mAdapter;
    List<Contract> mContractList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contract);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        mUser = SpHelper.getInstance(this).getUser();
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mContractRv.setLayoutManager(layoutManager);
        mContractRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new SignContractAdapter(this, R.layout.item_select_contract, mContractList);
        mContractRv.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        String url = Constant.APP_BASE_URL +
                "contract/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "&phone=" + mUser.getPhone() +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;
        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mContractSrl.setRefreshing(false);
            PageInfo<Contract> contractPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Contract>>() {
            });
            List<Contract> contractList = contractPageInfo.getList();

            if (mPage == 1) {
                mContractList.clear();
            }
            mContractList.addAll(contractList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mContractList.size() >= contractPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mContractSrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(SelectContractActivity.this, volleyError);
        });

    }

    private void initListener() {
        mContractSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mContractRv);
        mAdapter.setOnItemClickListener(this);

    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mContractSrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Contract contract = mContractList.get(position);
        Intent intent = new Intent();
        intent.putExtra("contract", contract);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLoadMoreRequested() throws InterruptedException {
        if (mContractSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}