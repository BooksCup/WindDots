package com.wd.winddots.activity.order;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.select.OrderAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Order;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_order)
    RecyclerView mOrderRv;

    @BindView(R.id.srl_order)
    SwipeRefreshLayout mOrderSrl;

    @BindView(R.id.et_search)
    EditText mSearchEt;

    OrderAdapter mOrderAdapter;
    VolleyUtil mVolleyUtil;
    List<Order> mOrderList = new ArrayList<>();

    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mOrderRv.setLayoutManager(layoutManager);
        mOrderRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mOrderAdapter = new OrderAdapter(R.layout.item_order, mOrderList);
        mOrderRv.setAdapter(mOrderAdapter);
        getData();
    }

    public void initListener() {
        mOrderSrl.setOnRefreshListener(this);
        mOrderAdapter.setOnLoadMoreListener(this, mOrderRv);
    }

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                showLoadingDialog();
                mKeyword = mSearchEt.getText().toString();
                mPage = 1;
                hideKeyboard();
                getData();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mOrderSrl.setRefreshing(true);
        mOrderAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&pageNum=" + mPage +
                    "&pageSize=" + mPageSize + "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&pageNum=" + mPage +
                    "&pageSize=" + mPageSize + "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mOrderSrl.setRefreshing(false);
            PageInfo<Order> orderPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Order>>() {
            });
            List<Order> orderList = orderPageInfo.getList();
            if (mPage == 1) {
                mOrderList.clear();
            }
            mOrderList.addAll(orderList);
            mOrderAdapter.setKeyword(mKeyword);
            mOrderAdapter.notifyDataSetChanged();
            mOrderAdapter.loadMoreComplete();
            if (mOrderList.size() >= orderPageInfo.getTotal()) {
                mOrderAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mOrderSrl.setRefreshing(false);
            mOrderAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(OrderActivity.this, volleyError);
        });
    }


    @Override
    public void onLoadMoreRequested() throws InterruptedException {
        if (mOrderSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

}