package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.BottomSearchBarView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener,
BottomSearchBarView.BottomSearchBarViewClickListener{

    public static final int REQUEST_ADD_STOCK_IN_APPLY = 0;
    public static final int REQUEST_FABRIC_CHECK_TASK = 1;

    @BindView(R.id.rv_order)
    RecyclerView mOrderRv;

    @BindView(R.id.srl_order)
    SwipeRefreshLayout mOrderSrl;

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.bottom_bar)
    BottomSearchBarView mBottomSearchBarView;

    OrderAdapter mOrderAdapter;
    VolleyUtil mVolleyUtil;
    List<Order> mOrderList = new ArrayList<>();

    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";
    int request;
//    String mGoodsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        request = getIntent().getIntExtra("request", 0);
//        mGoodsId = getIntent().getStringExtra("goodsId");
        initView();
        initListener();
    }

    private void initView() {

        mBottomSearchBarView.setOnIconClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mOrderRv.setLayoutManager(layoutManager);
        mOrderRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mOrderAdapter = new OrderAdapter(R.layout.item_select_order, mOrderList);
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
                mPage = 1;
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
        mOrderAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        mOrderSrl.setRefreshing(true);
        mOrderAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mOrderSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final Order order = mOrderList.get(position);
        if (REQUEST_ADD_STOCK_IN_APPLY == request) {
            // 入库申请(入库单)
            Intent intent = new Intent();
            intent.putExtra("order", order);
            setResult(RESULT_OK, intent);
            finish();
        } else if (REQUEST_FABRIC_CHECK_TASK == request) {
            showLoadingDialog();
            // 拉取订单信息
            // 新增盘点任务
            addFabricCheckTask(order);
        } else {
            Intent intent = new Intent();
            intent.putExtra("orderId", order.getOrderId());
            intent.putExtra("orderNo", order.getOrderNo());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    private void getData() {
        String url;
        try {
//            if (TextUtils.isEmpty(mGoodsId)) {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&pageNum=" + mPage +
                    "&pageSize=" + mPageSize + "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
//            } else {
//                url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + mPage +
//                        "&pageSize=" + mPageSize + "&goodsId=" + mGoodsId +
//                        "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
//            }
        } catch (UnsupportedEncodingException e) {
//            if (TextUtils.isEmpty(mGoodsId)) {
            url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&pageNum=" + mPage +
                    "&pageSize=" + mPageSize + "&keyword=" + mKeyword;
//            } else {
//                url = Constant.APP_BASE_URL + "order/search?enterpriseId=" + "1" + "&pageNum=" + mPage +
//                        "&pageSize=" + mPageSize + "&goodsId=" + mGoodsId +
//                        "&keyword=" + mKeyword;
//            }
        }
        Log.e("net666",url);

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
            mVolleyUtil.handleCommonErrorResponse(SelectOrderActivity.this, volleyError);
        });
    }

    /**
     * 新增盘点任务
     *
     * @param order 订单信息
     */
    private void addFabricCheckTask(final Order order) {
        String url = Constant.APP_BASE_URL + "fabricCheckTask";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("goodsId", Utils.nullOrEmpty(order.getGoodsId()));
        paramMap.put("goodsName", Utils.nullOrEmpty(order.getGoodsName()));
        paramMap.put("goodsNo", Utils.nullOrEmpty(order.getGoodsNo()));
        paramMap.put("goodsPhotos", order.getGoodsPhotos());
        paramMap.put("relatedCompanyId", Utils.nullOrEmpty(order.getRelatedCompanyId()));
        paramMap.put("relatedCompanyName", Utils.nullOrEmpty(order.getRelatedCompanyName()));
        paramMap.put("relatedCompanyShortName", Utils.nullOrEmpty(order.getRelatedCompanyShortName()));
        paramMap.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        paramMap.put("orderId", Utils.nullOrEmpty(order.getOrderId()));
        paramMap.put("orderNo", Utils.nullOrEmpty(order.getOrderNo()));
        paramMap.put("orderTheme", Utils.nullOrEmpty(order.getOrderTheme()));
        paramMap.put("deliveryDate", Utils.nullOrEmpty(order.getDeliveryDates()));

        Log.e("net666",JSON.toJSONString(paramMap));

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            Log.e("net666",response);
            showToast(getString(R.string.add_fabric_check_task_success));
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(SelectOrderActivity.this, volleyError);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(SelectOrderActivity.this, AddSimpleOrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSearchIconDidClick() {

    }
}