package com.wd.winddots.activity.stock.out;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.work.AddDeliveryActivity;
import com.wd.winddots.activity.work.DeliveryAdapter;
import com.wd.winddots.activity.work.OnRecyclerItemClickListener;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.filter.FilterView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StockOutActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, OnRecyclerItemClickListener {
    @BindView(R.id.filterView)
    FilterView mFilterView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.delivery_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.view_searchBar)
    ListBottomBar mSearchBarView;

    DeliveryAdapter mAdapter;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        initView();
        initListener();
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
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        mAdapter = new DeliveryAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void initListener() {
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int Position) {
        Intent intent = new Intent(StockOutActivity.this, AddDeliveryActivity.class);
        startActivity(intent);
    }
}
