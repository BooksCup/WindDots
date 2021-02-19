package com.wd.winddots.activity.select;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.adapter.select.OrderAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Order;
import com.wd.winddots.utils.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择订单
 *
 * @author zhou
 */
public class SelectOrderActivity extends FragmentActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_order)
    RecyclerView mOrderRv;

    @BindView(R.id.srl_order)
    SwipeRefreshLayout mOrderSrl;

    OrderAdapter mOrderAdapter;
    VolleyUtil mVolleyUtil;
    List<Order> mOrderList = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;

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

    public void initListener() {
        mOrderSrl.setOnRefreshListener(this);
        mOrderAdapter.setOnLoadMoreListener(this, mOrderRv);
    }

    private void getData() {
        String keyword = "";
        String url;
        try {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + page + "&pageSize=" + pageSize +
                    "&keyword=" + URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + page + "&pageSize=" + pageSize +
                    "&keyword=" + keyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            mOrderSrl.setRefreshing(false);
            List<Order> orderList = JSON.parseArray(response, Order.class);
            if (page == 1) {
                mOrderList.clear();
            }
            mOrderList.addAll(orderList);
            mOrderAdapter.notifyDataSetChanged();
            mOrderAdapter.loadMoreComplete();

        }, volleyError -> {
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
