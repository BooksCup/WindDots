package com.wd.winddots.activity.stock.out;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.stock.in.StockInDetailActivity;
import com.wd.winddots.adapter.stock.out.StockOutApplyAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.StockOutApply;
import com.wd.winddots.enums.RoleEnum;
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
 * 出库
 *
 * @author zhou
 */
public class StockOutActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_stock_out_apply)
    RecyclerView mStockOutApplyRv;

    @BindView(R.id.srl_stock_out_apply)
    SwipeRefreshLayout mStockOutApplySrl;

    StockOutApplyAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<StockOutApply> mStockOutApplyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
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
        mStockOutApplySrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mStockOutApplySrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockOutApplyRv.setLayoutManager(layoutManager);
        mStockOutApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StockOutApplyAdapter(R.layout.item_stock_out_apply, mStockOutApplyList);
        mStockOutApplyRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mStockOutApplySrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(StockOutActivity.this, mStockOutApplyRv);
        mAdapter.setOnItemClickListener(this);
    }


    private void getData() {
        String url = Constant.APP_BASE_URL + "stockApplication?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "&stockType=" + Constant.STOCK_TYPE_OUT +
                "&role=" + RoleEnum.ROLE_WAREHOUSE_KEEPER.getCode() +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mStockOutApplySrl.setRefreshing(false);
            PageInfo<StockOutApply> stockOutApplyPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<StockOutApply>>() {
            });
            List<StockOutApply> stockOutApplyList = stockOutApplyPageInfo.getList();

            if (mPage == 1) {
                mStockOutApplyList.clear();
            }
            mStockOutApplyList.addAll(stockOutApplyList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mStockOutApplyList.size() >= stockOutApplyPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mStockOutApplySrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(StockOutActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StockOutApply stockOutApply = mStockOutApplyList.get(position);
        Intent intent = new Intent(StockOutActivity.this, OfficeSuppliesOutDetailActivity.class);
        intent.putExtra("stockOutApplyId", stockOutApply.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

}
