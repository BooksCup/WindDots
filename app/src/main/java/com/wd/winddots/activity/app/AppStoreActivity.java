package com.wd.winddots.activity.app;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.app.AppStoreAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.App;
import com.wd.winddots.entity.PageInfo;
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

public class AppStoreActivity extends BaseActivity implements
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srl_app)
    SwipeRefreshLayout mAppSrl;

    @BindView(R.id.rv_app)
    RecyclerView mAppRv;

    private AppStoreAdapter mAdapter;
    private List<App> mAppList = new ArrayList<>();

    int mPage = 1;
    int mPageSize = 10;
    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAppRv.setLayoutManager(layoutManager);
        mAppRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new AppStoreAdapter(R.layout.item_app_store, mAppList);
        mAppRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mAppSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mAppRv);
    }

    public void initData() {
        mAppSrl.setRefreshing(true);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getData() {
        String url = Constant.APP_BASE_URL + "app?userId=" + SpHelper.getInstance(this).getUserId() +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mAppSrl.setRefreshing(false);
            PageInfo<App> appPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<App>>() {
            });
            List<App> appList = appPageInfo.getList();

            if (mPage == 1) {
                mAppList.clear();
            }
            mAppList.addAll(appList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mAppList.size() >= appPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mAppSrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(AppStoreActivity.this, volleyError);
        });
    }

    @Override
    public void onLoadMoreRequested() {
        if (mAppSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onRefresh() {
        mAppSrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }
}
