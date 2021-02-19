package com.wd.winddots.desktop.list.invoice.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.invoice.adapter.InvoiceListAdapter;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListBean;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.filter.FilterBean;
import com.wd.winddots.desktop.view.filter.FilterView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: InvoiceListActivity
 * Author: 郑
 * Date: 2020/7/1 10:48 AM
 * Description: 发票列表
 */
public class InvoiceListActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener,
        ListBottomBar.ListBottomBarActionListener,
        FilterView.FilterViewBottomBarActionListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private List<InvoiceListBean.InvoiceItem> mDataSource = new ArrayList<>();

    private FilterView mFilterView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private InvoiceListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListBottomBar mSearchBarView;


    @Override
    public int getContentView() {
        return R.layout.activity_invoice_list;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mFilterView = findViewById(R.id.filterView);
        mFilterView.setTitle(mContext.getString(R.string.invoice_filter));
        mFilterView.setFilterViewBottomBarActionListener(this);

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new InvoiceListAdapter(R.layout.item_invoice_list, mDataSource);
        mRecyclerView.setAdapter(mAdapter);

        mSearchBarView = findViewById(R.id.view_searchBar);
        mSearchBarView.setListBottomBarActionListener(this);

        ImageView backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {

        getData();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        page = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() throws InterruptedException {
        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        page += 1;
        getData();
    }

    private void getData() {

        Map data = new HashMap();
        data.put("compId", SpHelper.getInstance(mContext).getEnterpriseId());
        data.put("page", page);
        data.put("pageSize", pageSize);
        data.put("groupFlag", 0);
        data.put("exchangeGroupFlag", 0);
        RequestBody requestBody = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.getInvoiceList(requestBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        InvoiceListBean bean = gson.fromJson(s, InvoiceListBean.class);
                        if (page == 1) {
                            mDataSource.clear();
                        }
                        mDataSource.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.loadMoreComplete();
                        if (mDataSource.size() >= bean.getTotalCount()) {
                            mAdapter.setEnableLoadMore(false);
                        }
                    }
                })
        );

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, InvoiceGroupActivity.class);
        InvoiceListBean.InvoiceItem invoiceItem = mDataSource.get(position);
        intent.putExtra("enterpriseName", invoiceItem.getExchangeEnterpriseName());
        startActivity(intent);
    }

    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(mContext, InvoiceAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSearchIconDidClick() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }


    @Override
    public void onResetBtnDidClick() {

    }

    @Override
    public void onCommitBtnDidClick() {
        List<FilterBean> list = mFilterView.getFilterList();
        mSearchBarView.setData(list);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String resultStr = data.getStringExtra("result");
        Log.e("result", resultStr);
        showToast(resultStr);
    }
}
