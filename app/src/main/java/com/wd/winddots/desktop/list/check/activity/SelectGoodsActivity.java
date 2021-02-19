package com.wd.winddots.desktop.list.check.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.adapter.CheckGoodsListAdapter;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
import com.wd.winddots.desktop.view.CheckFilter;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
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

/**
 * FileName: SelectGoodsActivity
 * Author: 郑
 * Date: 2021/1/14 3:00 PM
 * Description: 选择物品进行盘点
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class SelectGoodsActivity extends FragmentActivity implements View.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener, ListBottomBar.ListBottomBarActionListener, CheckGoodsListAdapter.OnSubItemClickListener, CheckFilter.CheckFilterOnCommitClickListener {

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
    private CheckGoodsListAdapter mAdapter;

    private LoadingDialog mDialog;


    private boolean mIsLoading = false;
    private boolean mEndLoading = false;
    private int mPage = 1;

    private String mSearchKey = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_select_goods);
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
        mAdapter = new CheckGoodsListAdapter();
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
        //mAdapter.setEnableLoadMore(true);
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
        String url = Constant.APP_BASE_URL + "fabricQcWarehouse/getAppPage?pageNum=" + mPage + "&pageSize=15" + "&enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + mSearchKey;
        Log.e("net666", url);
        mIsLoading = true;
        mVolleyUtil.httpGetRequest(url, response -> {
            mDialog.hide();
            mIsLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            CheckGoodsBean goodsBean = null;
            try {
                goodsBean = JSON.parseObject(response, CheckGoodsBean.class);
            } catch (Exception ignored) {
            }
            if (goodsBean == null) {
                return;
            }
            List<CheckGoodsBean.CheckGoodsItem> goodsItemList = goodsBean.getData();
            if (goodsItemList == null) {
                return;
            }
            List<ExpandGroupItemEntity<CheckGoodsBean.CheckGoodsItem, CheckGoodsBean.CheckGang>> dataList = new ArrayList<>();
            for (int i = 0; i < goodsItemList.size(); i++) {
                CheckGoodsBean.CheckGoodsItem item = goodsItemList.get(i);
                ExpandGroupItemEntity<CheckGoodsBean.CheckGoodsItem, CheckGoodsBean.CheckGang> group = new ExpandGroupItemEntity<>();
                List<CheckGoodsBean.CheckEnterprise> enterprises = item.getQcWarehouseSupplierIdVos();
                List<CheckGoodsBean.CheckGang> gangList = new ArrayList<>();
                for (int m = 0; m < enterprises.size(); m++) {
                    CheckGoodsBean.CheckEnterprise enterpriseItem = enterprises.get(m);
                    List<CheckGoodsBean.CheckGang> checkGangs = enterpriseItem.getFabricQcWarehouseList();
                    if (checkGangs.size() > 0) {
                        checkGangs.get(0).setEnterprise(enterpriseItem);
                    }
                    gangList.addAll(checkGangs);
                }
                group.setParent(item);
                group.setChildList(gangList);
                group.setExpand(false);
                dataList.add(group);
            }
            List<ExpandGroupItemEntity<CheckGoodsBean.CheckGoodsItem, CheckGoodsBean.CheckGang>> oldData = mAdapter.getData();
            if (mPage == 1) {
                mAdapter.setData(dataList);
            } else {
                oldData.addAll(dataList);
                mAdapter.setData(oldData);
            }
            if (mAdapter.getData().size() >= goodsBean.getTotalCount()) {
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
        Intent intent = new Intent(this, CheckActivity.class);
        intent.putExtra("warehouseId", item.getId());
        intent.putExtra("goodsName", Utils.nullOrEmpty(item.getGoodsName()));
        intent.putExtra("goodsNo", Utils.nullOrEmpty(item.getGoodsNo()));
        intent.putExtra("oddTime", Utils.nullOrEmpty(item.getOddTime()));
        intent.putExtra("cylinderNumber", Utils.nullOrEmpty(item.getCylinderNumber()));

        startActivity(intent);
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
}
