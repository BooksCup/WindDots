package com.wd.winddots.desktop.activity;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.adapter.StoreAdapter;
import com.wd.winddots.desktop.bean.DesktopListBean;
import com.wd.winddots.desktop.presenter.impl.StorePresenterImpl;
import com.wd.winddots.desktop.presenter.view.StoreView;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class StoreActivity extends CommonActivity<StoreView, StorePresenterImpl> implements StoreView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StoreAdapter mAdapter;
    private View mFooterView;
    private ArrayList<DesktopListBean.StoreListBena> mDataSource = new ArrayList<>();


    private String userId;
    private String enterpriseId;
    private Integer pageNum = 1;

    private List<Map> ids;


    @Override
    public StorePresenterImpl initPresenter() {
        return new StorePresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("应用市场");
        addBadyView(R.layout.activity_store);
        mSwipeRefreshLayout = findViewById(R.id.activity_store_srl);
        mRecyclerView = findViewById(R.id.activity_store_rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StoreAdapter(R.layout.item_store, mDataSource);

        mRecyclerView.setAdapter(mAdapter);
        userId = SpHelper.getInstance(getApplicationContext()).getUserId();
        enterpriseId = SpHelper.getInstance(getApplicationContext()).getEnterpriseId();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (data != null) {
            Gson gson = new Gson();
            List<Map> list = gson.fromJson(data, new TypeToken<List<Map>>() {
            }.getType());
            ids = list;
        } else {
            ids = new ArrayList<>();
        }
        mAdapter.setIds(ids);

    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.getStoreList(userId, pageNum, 10);
    }


    /*
     * 获取列表
     * */
    @Override
    public void getStoreListSuccess(DesktopListBean bean) {
        if (pageNum == 1) {
            mDataSource.clear();
        }
        if (bean.getList().size() < 10) {
            mAdapter.setEnableLoadMore(false);
            if (mFooterView == null) {
//                mFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_no_more_data,null,false);
//                mAdapter.addFooterView(mFooterView);
            }
        }

        mSwipeRefreshLayout.setRefreshing(false);
        mDataSource.addAll(bean.getList());
        mAdapter.notifyDataSetChanged();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void getStoreListError() {
        mAdapter.loadMoreFail();
        if (pageNum > 1) {
            pageNum -= 1;
        }

        showToast("加载失败,请稍后重试");
    }

    @Override
    public void getStoreListCompleted() {
        mAdapter.loadMoreComplete();
    }


    @Override
    public void onLoadMoreRequested() {

        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        pageNum += 1;
        presenter.getStoreList(userId, pageNum, 10);

    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        mAdapter.setEnableLoadMore(true);
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.getStoreList(userId, pageNum, 10);
    }
}
