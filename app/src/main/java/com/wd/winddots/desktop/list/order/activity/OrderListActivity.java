package com.wd.winddots.desktop.list.order.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.order.adapter.OrderListAdapter;
import com.wd.winddots.desktop.list.order.bean.OrderListBean;
import com.wd.winddots.desktop.list.quote.bean.QuoteListBean;
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
 * FileName: OrderListActivity
 * Author: 郑
 * Date: 2020/8/7 12:28 PM
 * Description: 订单列表
 */
public class OrderListActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener,
        ListBottomBar.ListBottomBarActionListener,
        FilterView.FilterViewBottomBarActionListener {


    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private List<OrderListBean.OrderListItem> mDataSource = new ArrayList<>();

    private FilterView mFilterView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private OrderListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListBottomBar mSearchBarView;


    @Override
    public int getContentView() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mFilterView = findViewById(R.id.filterView);
        mFilterView.setTitle(mContext.getString(R.string.order_filter));
        mFilterView.setFilterViewBottomBarActionListener(this);

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new OrderListAdapter(R.layout.item_order_list, mDataSource,mContext);
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
        mSwipeRefreshLayout.setRefreshing(true);
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
        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        data.put("fromEnterpriseId", enterpriseId);
        data.put("pageNum", page);
        data.put("numPerPage", pageSize);
        data.put("sort", "desc");
        data.put("sortSearch", "o.create_time");
//        data.put("groupFlag", 0);
//        data.put("exchangeGroupFlag", 0);
        RequestBody requestBody = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.getOrderList(requestBody,enterpriseId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("net666", String.valueOf(e));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        OrderListBean bean = gson.fromJson(s, OrderListBean.class);
                        List<OrderListBean.OrderListItem> list = bean.getList();
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        for (int n = 0;n < list.size();n++){
                            OrderListBean.OrderListItem item = list.get(n);
                            String goodsPhotosJson = item.getGoodsPhotos();
                            String goodsJson = item.getGoodsPhotos();

                            List<String> goodsPhotos = new ArrayList<>();
                            QuoteListBean.GoodsInfo goodsInfo;
                            try {
                                goodsPhotos = gson.fromJson(goodsPhotosJson, new TypeToken<List<String>>() {
                                }.getType());

                            } catch (Exception e) {
                                goodsPhotos = new ArrayList<>();
                            }
                            try {
                                goodsInfo = gson.fromJson(goodsJson, QuoteListBean.GoodsInfo.class);
                            } catch (Exception e) {
                                goodsInfo = new QuoteListBean.GoodsInfo();
                            }

                            if (goodsPhotos != null && goodsPhotos.size() > 0) {
                                String imageurl = "http:" + goodsPhotos.get(0);
                                item.setPhoto(imageurl);
                            }
                        }
                        if (page == 1) {
                            mDataSource.clear();
                        }
                        mDataSource.addAll(bean.getList());
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
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        OrderListBean.OrderListItem item = mDataSource.get(position);
        Gson gson = new Gson();
        String jsonS = gson.toJson(item);
        intent.putExtra("data", jsonS);
        startActivity(intent);
    }

    @Override
    public void onAddIconDidClick() {
//        Intent intent = new Intent(mContext, InvoiceAddActivity.class);
//        startActivity(intent);
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
