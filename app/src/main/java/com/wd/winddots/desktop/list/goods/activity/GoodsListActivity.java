package com.wd.winddots.desktop.list.goods.activity;

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
import com.wd.winddots.desktop.list.goods.adapter.GoodsListAdapter;
import com.wd.winddots.desktop.list.goods.bean.GoodsListBean;
import com.wd.winddots.desktop.list.goods.bean.GoodsTypeBean;
import com.wd.winddots.desktop.list.goods.filter.GoodsFilterView;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.filter.FilterBean;
import com.wd.winddots.desktop.view.filter.FilterView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Description: 报价列表
 */
public class GoodsListActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener,
        ListBottomBar.ListBottomBarActionListener,
        FilterView.FilterViewBottomBarActionListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private int page = 1;
    private int pageSize = 10;

    private List<GoodsListBean.GoodsListItem> mDataSource = new ArrayList<>();

    private GoodsFilterView mFilterView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private GoodsListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListBottomBar mSearchBarView;


    @Override
    public int getContentView() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mFilterView = findViewById(R.id.filterView);
        mFilterView.setFilterViewBottomBarActionListener(this);

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new GoodsListAdapter(R.layout.item_goods_list, mDataSource);
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
        getGoodsTypeList();
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
        data.put("enterpriseId", SpHelper.getInstance(mContext).getEnterpriseId());
        data.put("pageNum", page);
        data.put("numPerPage", pageSize);
        data.put("goodsTypeId", "");
        RequestBody requestBody = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.getGoodsList(requestBody,SpHelper.getInstance(mContext).getEnterpriseId()).
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
                        GoodsListBean bean = gson.fromJson(s, GoodsListBean.class);
                        List<GoodsListBean.GoodsListItem> list = bean.getList();
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        for (int n = 0; n < list.size(); n++) {
                            GoodsListBean.GoodsListItem item = list.get(n);
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

                            String title1 = Utils.nullOrEmpty(item.getGoodsNo()) + " (" + Utils.nullOrEmpty(item.getGoodsName()) + ")";
                            Map<String, JSONArray> attrList = Utils.getMapForJson(item.getAttrList());
                            List<String> attrSList = new ArrayList<>();
                            if (attrList != null) {
                                for (Map.Entry<String, JSONArray> entry : attrList.entrySet()) {
                                    //获取key
                                    String key = entry.getKey();
                                    //获取value
                                    JSONArray value = entry.getValue();
                                    JSONObject valueMap = null;
                                    try {
                                        valueMap = value.getJSONObject(0);
                                    }catch (Exception e){
                                    }
                                    if (valueMap != null){
                                        try {
                                            attrSList.add(key + ": " + valueMap.getString("name"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (attrSList.size() >=2){
                                item.setAttr1(attrSList.get(0));
                                item.setAttr2(attrSList.get(1));
                            }else if (attrSList.size() == 1){
                                item.setAttr1(attrSList.get(0));
                            }
                            String number = Utils.numberNullOrEmpty(item.getResidualNumber()) + Utils.nullOrEmpty(item.getGoodsUnit());
                            item.setNumber(number);
                            item.setGoodsNameNo(title1);
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


    private void getGoodsTypeList(){
        compositeSubscription.add(dataManager.getGoodsTypeList(SpHelper.getInstance(mContext).getEnterpriseId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        GoodsTypeBean bean = gson.fromJson(s, GoodsTypeBean.class);
                        GoodsTypeBean.GoodsTypeItem allItem = new GoodsTypeBean.GoodsTypeItem();
                        allItem.setId("");
                        allItem.setName(mContext.getString(R.string.goods_type_all_filter));
                        allItem.setSelect(true);
                        bean.getList().add(0,allItem);
                        mFilterView.setGoodsTypeList(bean.getList());
                    }
                })
        );
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
        GoodsListBean.GoodsListItem item = mDataSource.get(position);
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
