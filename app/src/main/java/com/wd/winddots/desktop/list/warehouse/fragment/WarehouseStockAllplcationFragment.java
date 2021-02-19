package com.wd.winddots.desktop.list.warehouse.fragment;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.warehouse.adapter.WarehouseStockApplicationAdapter;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseStockApplicationBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.view.TimeLineHeaderFooterView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: WarehouseStockAllplcationFragment
 * Author: 郑
 * Date: 2020/8/7 9:44 AM
 * Description: 仓库物流
 */
public class WarehouseStockAllplcationFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {



    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private String mWarehouseId;
    private List<WarehouseStockApplicationBean.WarehouseStockApplicationItem> mDataSource = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private WarehouseStockApplicationAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static BaseFragment newInstance(String warehouseId) {
        WarehouseStockAllplcationFragment frament = new WarehouseStockAllplcationFragment();
        frament.compositeSubscription = new CompositeSubscription();
        frament.dataManager = new ElseDataManager();
        frament.mWarehouseId = warehouseId;
        return frament;
    }


    @Override
    public BasePresenter initPresenter() {
        return new  BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_warehouse_stock_application;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.srl);
        mRecyclerView = mView.findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new WarehouseStockApplicationAdapter(R.layout.item_warehouse_stock_application, mDataSource,mContext);
        TimeLineHeaderFooterView footerView = new TimeLineHeaderFooterView(mContext);
        footerView.setmText(mContext.getString(R.string.time_line_footer));
        mAdapter.setFooterView(footerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
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


    private void getData(){
        compositeSubscription.add(dataManager.getWarehouseStockApplication(mWarehouseId,page,pageSize).
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
                        WarehouseStockApplicationBean bean = gson.fromJson(s, WarehouseStockApplicationBean.class);
                        List<WarehouseStockApplicationBean.WarehouseStockApplicationItem> list = bean.getData();
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        for (int n = 0;n < list.size();n++){
                            WarehouseStockApplicationBean.WarehouseStockApplicationItem item = list.get(n);
                            String type = "";
                            switch (item.getBizType()) {
                                case "1":
                                    type = "成品销售";
                                    break;
                                case "2":
                                    type = "材料销售";
                                    break;
                                case "3":
                                    type = "成品入库";
                                    break;
                                case "4":
                                    type = "材料入库";
                                    break;
                                case "5":
                                    type = "生产领用";
                                    break;
                                case "6":
                                    type = "完工入库";
                                    break;
                                case "7":
                                    type = "固定资产入库";
                                    break;
                                case "8":
                                    type = "办公用品入库";
                                    break;
                                case "9":
                                    type = "退货入库";
                                    break;
                                case "10":
                                    type = "固定资产领用";
                                    break;
                                case "11":
                                    type = "办公用品领用";
                                    break;
                                case "12":
                                    type = "固定资产处理";
                                    break;
                                default:
                            }
                            item.setTypeName(type);
                        }
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
}
