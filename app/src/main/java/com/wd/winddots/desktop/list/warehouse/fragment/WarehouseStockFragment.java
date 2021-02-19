package com.wd.winddots.desktop.list.warehouse.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.warehouse.adapter.WarehouseStockAdapter;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseStockBean;
import com.wd.winddots.net.ElseDataManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: WarehouseStockFragment
 * Author: 郑
 * Date: 2020/8/5 9:47 AM
 * Description: 仓库库存
 */
public class WarehouseStockFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private String mWarehouseId;

    private List<WarehouseStockBean.WarehouseStockItem> mDataSource = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private WarehouseStockAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static BaseFragment newInstance(String warehouseId) {
        WarehouseStockFragment frament = new WarehouseStockFragment();
        frament.compositeSubscription = new CompositeSubscription();
        frament.dataManager = new ElseDataManager();
        frament.mWarehouseId = warehouseId;
        return frament;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_warehouse_stock;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = mView.findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new WarehouseStockAdapter(R.layout.item_warehouse_stock, mDataSource,mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }

    private void getData(){
        compositeSubscription.add(dataManager.getWarehouseStockList(mWarehouseId).
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
                        WarehouseStockBean bean = gson.fromJson(s, WarehouseStockBean.class);
                        List<WarehouseStockBean.WarehouseStockItem> list = bean.getData().getStockMap();
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        for (int n = 0;n < list.size();n++){
                            WarehouseStockBean.WarehouseStockItem item = list.get(n);
                            String goodsPhotosJson = item.getGoodsPhotos();
                            List<String> goodsPhotos = new ArrayList<>();
                            try {
                                goodsPhotos = gson.fromJson(goodsPhotosJson, new TypeToken<List<String>>() {
                                }.getType());

                            } catch (Exception e) {
                                goodsPhotos = new ArrayList<>();
                            }
                            if (goodsPhotos != null && goodsPhotos.size() > 0) {
                                String imageurl = "http:" + goodsPhotos.get(0);
                                item.setPhoto(imageurl);
                            }
                        }
                        mDataSource.clear();
                        mDataSource.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.loadMoreComplete();
                    }
                })
        );
    }


}
