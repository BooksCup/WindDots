package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.work.AddDeliveryActivity;
import com.wd.winddots.activity.work.OnRecyclerItemClickListener;
import com.wd.winddots.adapter.stock.in.StockInApplyAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 入库申请(入库单)
 *
 * @author zhou
 */
public class StockInApplyActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, OnRecyclerItemClickListener {

    @BindView(R.id.rv_stock_in_apply)
    RecyclerView mStockInApplyRv;

    @BindView(R.id.srl_stock_in_apply)
    SwipeRefreshLayout mStockInApplySrl;

    StockInApplyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in_apply);
        initView();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(StockInApplyActivity.this, AddStockInApplyActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockInApplyRv.setLayoutManager(layoutManager);
        mStockInApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StockInApplyAdapter();
        mStockInApplyRv.setAdapter(mAdapter);
    }

    public void initListener() {
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int Position) {
        Intent intent = new Intent(StockInApplyActivity.this, AddDeliveryActivity.class);
        startActivity(intent);
    }
}
