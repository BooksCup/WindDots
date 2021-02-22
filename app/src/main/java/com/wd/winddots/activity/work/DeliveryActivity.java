package com.wd.winddots.activity.work;

import android.content.Intent;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.filter.FilterView;

import butterknife.BindView;

public class DeliveryActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,OnRecyclerItemClickListener{

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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() throws InterruptedException {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new  LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new DeliveryAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(int Position) {
        Intent intent = new Intent(DeliveryActivity.this,AddDeliveryActivity.class);
        startActivity(intent);
    }
}
