package com.wd.winddots.desktop.list.invoice.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.list.invoice.adapter.InvoiceListGroupAdapter;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListGroupBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
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
 * FileName: InvoiceGroupActivity
 * Author: 郑
 * Date: 2020/7/1 12:12 PM
 * Description: 发票列表二级页面
 */
public class InvoiceGroupActivity extends CommonActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;
    private String enterpriseName = "";

    private List<InvoiceListGroupBean.InvoiceDetail> mDataSource = new ArrayList<>();


    private RecyclerView mRecyclerView;
    private InvoiceListGroupAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        super.initView();
        setTitleText(mContext.getString(R.string.invoice_title));
        addBadyView(R.layout.activity_invoice_group);

        Intent intent = getIntent();
        enterpriseName = intent.getStringExtra("enterpriseName");
        //showToast(enterpriseName);

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new InvoiceListGroupAdapter(R.layout.item_invoice_group, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
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
        data.put("page", page);
        data.put("pageSize", pageSize);
        data.put("exchangeEnterpriseName",enterpriseName);
        data.put("exchangeEnterpriseId",0);
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
                        InvoiceListGroupBean bean = gson.fromJson(s, InvoiceListGroupBean.class);
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
        Intent intent = new Intent(mContext, InvoiceDetailActivity.class);
        Gson gson = new Gson();
        String jsonS = gson.toJson(mDataSource.get(position));
        intent.putExtra("id",mDataSource.get(position).getId());
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        //onRefresh();
    }
}
