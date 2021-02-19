package com.wd.winddots.desktop.list.warehouse.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.warehouse.activity.WarehouseDetailActivity;
import com.wd.winddots.desktop.list.warehouse.adapter.WarehouseListAdapter;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseListBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * FileName: SubWarehouseFragment
 * Author: 郑
 * Date: 2020/7/31 4:36 PM
 * Description: 二级仓库
 */
public class SubWarehouseFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener{

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private String mWarehouseId;
    private List<WarehouseListBean.WarehouseListItem> mDataSource = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private WarehouseListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static BaseFragment newInstance(String warehouseId) {
        SubWarehouseFragment frament = new SubWarehouseFragment();
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
        return R.layout.fragment_warehouse_sub;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.srl);
        mRecyclerView = mView.findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new WarehouseListAdapter(R.layout.item_warehouse_list, mDataSource,mContext);
        mRecyclerView.setAdapter(mAdapter);
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


    private void getData(){
        Map data = new HashMap();
        //data.put("enterpriseId", SpHelper.getInstance(mContext).getEnterpriseId());
        data.put("pageNum", page);
        data.put("numPerPage", pageSize);
//        data.put("sort", "desc");
//        data.put("sortSearch", "h.create_time");
        data.put("parentId",mWarehouseId);
//        data.put("groupFlag", 0);
//        data.put("exchangeGroupFlag", 0);
        RequestBody requestBody = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.getWarehouseList(requestBody, SpHelper.getInstance(mContext).getEnterpriseId()).
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
                        WarehouseListBean bean = gson.fromJson(s, WarehouseListBean.class);
                        List<WarehouseListBean.WarehouseListItem> list = bean.getList();
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        for (int n = 0;n < list.size();n++){

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
        Intent intent = new Intent(mContext, WarehouseDetailActivity.class);
        WarehouseListBean.WarehouseListItem item = mDataSource.get(position);
        Gson gson = new Gson();
        String jsonS = gson.toJson(item);
        intent.putExtra("data", jsonS);
        startActivity(intent);
    }
}
