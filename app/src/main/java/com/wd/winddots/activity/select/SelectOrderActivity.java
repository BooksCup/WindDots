package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.check.fabric.AddSimpleOrderActivity;
import com.wd.winddots.adapter.select.OrderAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Order;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
 * 选择订单
 *
 * @author zhou
 */
public class SelectOrderActivity extends BaseActivity
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

    private int page = 1;
    private int pageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);
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

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                showLoadingDialog();
                mKeyword = mSearchEt.getText().toString();
                page = 1;
                hideKeyboard();
                getData();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(SelectOrderActivity.this, AddSimpleOrderActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void initListener() {
        mOrderSrl.setOnRefreshListener(this);
        mOrderAdapter.setOnLoadMoreListener(this, mOrderRv);
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + page + "&pageSize=" + pageSize +
                    "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + page + "&pageSize=" + pageSize +
                    "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mOrderSrl.setRefreshing(false);
            PageInfo<Order> orderPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Order>>() {
            });
            List<Order> orderList = orderPageInfo.getList();

            if (page == 1) {
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
            mVolleyUtil.handleCommonErrorResponse(SelectOrderActivity.this, volleyError);
        });
    }

    @Override
    public void onRefresh() {
        mOrderSrl.setRefreshing(true);
        mOrderAdapter.setEnableLoadMore(true);
        page = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mOrderSrl.isRefreshing()) {
            return;
        }
        page += 1;
        getData();
    }

}