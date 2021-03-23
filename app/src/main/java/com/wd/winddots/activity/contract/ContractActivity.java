package com.wd.winddots.activity.contract;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.contract.ContractAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Contract;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合同
 *
 * @author zhou
 */
public class ContractActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_contract)
    RecyclerView mContractRv;

    @BindView(R.id.srl_contract)
    SwipeRefreshLayout mContractSrl;

    ContractAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<Contract> mContractList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
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
    public void onLoadMoreRequested() {
        if (mContractSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mContractRv.setLayoutManager(layoutManager);
        mContractRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ContractAdapter(this, R.layout.item_contract, mContractList);
        mContractRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mContractSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mContractRv);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Constant.APP_BASE_URL +
//                "contract?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "contract/search?enterpriseId=" + "1" +
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
            mVolleyUtil.handleCommonErrorResponse(ContractActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

}
