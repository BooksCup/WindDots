package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.stock.in.StockInApplyAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.StockInApply;
import com.wd.winddots.enums.RoleEnum;
import com.wd.winddots.enums.StockBizTypeEnum;
import com.wd.winddots.utils.SpHelper;
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
 * 入库
 *
 * @author zhou
 */
public class StockInActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_stock_in_apply)
    RecyclerView mStockInApplyRv;

    @BindView(R.id.srl_stock_in_apply)
    SwipeRefreshLayout mStockInApplySrl;

    StockInApplyAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<StockInApply> mStockInApplyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);
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
        mStockInApplySrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mStockInApplySrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockInApplyRv.setLayoutManager(layoutManager);
        mStockInApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StockInApplyAdapter(this, R.layout.item_stock_in_apply, mStockInApplyList);
        mStockInApplyRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mStockInApplySrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mStockInApplyRv);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Constant.APP_BASE_URL + "stockApplication?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "&stockType=" + Constant.STOCK_TYPE_IN +
                "&role=" + RoleEnum.ROLE_WAREHOUSE_KEEPER.getCode() +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mStockInApplySrl.setRefreshing(false);
            PageInfo<StockInApply> stockInApplyPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<StockInApply>>() {
            });
            List<StockInApply> stockInApplyList = stockInApplyPageInfo.getList();

            if (mPage == 1) {
                mStockInApplyList.clear();
            }
            mStockInApplyList.addAll(stockInApplyList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mStockInApplyList.size() >= stockInApplyPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mStockInApplySrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(StockInActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StockInApply stockInApply = mStockInApplyList.get(position);
        if (StockBizTypeEnum.STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN.getType().equals(stockInApply.getBizType())) {
            // 办公用品入库
            Intent intent = new Intent(StockInActivity.this, OfficeSuppliesInDetailActivity.class);
            intent.putExtra("stockInApplyId", stockInApply.getId());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

}