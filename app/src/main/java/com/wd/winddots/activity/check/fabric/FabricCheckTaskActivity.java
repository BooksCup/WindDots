package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wd.winddots.R;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
import com.wd.winddots.desktop.view.CheckFilter;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FabricCheckTaskActivity extends FragmentActivity
        implements View.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener,
        ListBottomBar.ListBottomBarActionListener, FabricCheckTaskAdapter.OnSubItemClickListener, CheckFilter.CheckFilterOnCommitClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_goods)
    PinnedHeaderRecyclerView mGoodsRv;

    @BindView(R.id.view_searchBar)
    ListBottomBar mBottomView;

    @BindView(R.id.goods_filter)
    CheckFilter mFilterView;

    private LinearLayoutManager mLayoutManager;
    private FabricCheckTaskAdapter mAdapter;

    private LoadingDialog mDialog;


    private boolean mIsLoading = false;
    private boolean mEndLoading = false;
    private int mPage = 1;

    private String mSearchKey = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_task);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        mDialog = LoadingDialog.getInstance(this);

        initView();
        getData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initView() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mBottomView.setListBottomBarActionListener(this);
        mFilterView.setOnCommitClickListener(this);
        mGoodsRv.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mGoodsRv.addItemDecoration(new PinnedHeaderItemDecoration());
        mAdapter = new FabricCheckTaskAdapter(this);
        mAdapter.setOnSubItemClickListener(this);
        mGoodsRv.setAdapter(mAdapter);
        mGoodsRv.setOnScrollChangeListener(this);

    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    /*
     * 下拉刷新
     * */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPage = 1;
        mEndLoading = false;
        getData();
    }

    /*
     * 获取数据
     * */
    private void getData() {

        if (mEndLoading) {
            return;
        }
        String url = Constant.APP_BASE_URL + "fabricCheckTask/search?keyword=&pageNum=" + mPage + "&pageSize=10" + "&enterpriseId=" + "1";//SpHelper.getInstance(this).getEnterpriseId();
        mIsLoading = true;
        Log.e("net666",url);
        mVolleyUtil.httpGetRequest(url, response -> {
            Log.e("net666",response);
            mDialog.hide();
            mIsLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            PageInfo<FabricCheckTask> fabricCheckTaskPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<FabricCheckTask>>() {
            });
            List<FabricCheckTask> fabricCheckTaskList = fabricCheckTaskPageInfo.getList();
            if (fabricCheckTaskList == null) {
                return;
            }
            List<ExpandGroupItemEntity<FabricCheckTask, FabricCheckLotInfo>> dataList = new ArrayList<>();
            for (int i = 0; i < fabricCheckTaskList.size(); i++) {
                FabricCheckTask item = fabricCheckTaskList.get(i);
                ExpandGroupItemEntity<FabricCheckTask, FabricCheckLotInfo> group = new ExpandGroupItemEntity<>();
                group.setParent(item);
                List<FabricCheckLotInfo> fabricCheckLotInfoList = item.getFabricCheckLotInfoList();
                if (null == fabricCheckLotInfoList) {
                    fabricCheckLotInfoList = new ArrayList<>();
                }
                //fabricCheckLotInfoList.add(0, new FabricCheckLotInfo());
                group.setChildList(fabricCheckLotInfoList);
                group.setExpand(false);
                dataList.add(group);
            }
            List<ExpandGroupItemEntity<FabricCheckTask, FabricCheckLotInfo>> oldData = mAdapter.getData();
            if (mPage == 1) {
                mAdapter.setData(dataList);
            } else {
                oldData.addAll(dataList);
                mAdapter.setData(oldData);
            }
            if (mAdapter.getData().size() >= fabricCheckTaskPageInfo.getTotal()) {
                mEndLoading = true;
            }
        }, volleyError -> {
            mSwipeRefreshLayout.setRefreshing(false);
            mDialog.hide();
            mIsLoading = false;
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });

    }


    /*
     * 点击底部加号
     * */
    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(this, SelectOrderActivity.class);
        startActivity(intent);
    }

    /*
     * 点击底部搜索
     * */
    @Override
    public void onSearchIconDidClick() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }

    /*
     * 点击子控件
     * */
    @Override
    public void onItemClick(CheckGoodsBean.CheckGang item) {
//        Intent intent = new Intent(this, CheckActivity.class);
//        intent.putExtra("warehouseId", item.getId());
//        intent.putExtra("goodsName", Utils.nullOrEmpty(item.getGoodsName()));
//        intent.putExtra("goodsNo", Utils.nullOrEmpty(item.getGoodsNo()));
//        intent.putExtra("oddTime", Utils.nullOrEmpty(item.getOddTime()));
//        intent.putExtra("cylinderNumber", Utils.nullOrEmpty(item.getCylinderNumber()));
//
//        startActivity(intent);
    }

    @Override
    public void onCommitBtnDidClick(Map<String, String> data) {
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
        String searchKey = "";
        for (Map.Entry<String, String> entry : data.entrySet()) {
            searchKey = searchKey + "&" + entry.getKey() + "=" + entry.getValue();
        }
        mSearchKey = searchKey;
        onRefresh();
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (mIsLoading || mEndLoading) {
            return;
        }
        getLastVisiblePosition();
    }

    private void getLastVisiblePosition() {
        int position;
        position = ((LinearLayoutManager) mGoodsRv.getLayoutManager()).findLastVisibleItemPosition();
        if (position == mAdapter.getItemCount() - 2) {
            mPage += 1;
            mDialog.show();
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        mEndLoading = false;
        getData();
    }
}
