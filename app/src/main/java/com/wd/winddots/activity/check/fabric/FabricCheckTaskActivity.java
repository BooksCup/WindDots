package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.view.CheckFilter;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wd.winddots.activity.select.SelectOrderActivity.REQUEST_FABRIC_CHECK_TASK;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FabricCheckTaskActivity extends FragmentActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ListBottomBar.ListBottomBarActionListener, CheckFilter.CheckFilterOnCommitClickListener, BaseQuickAdapter.OnItemClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_goods)
    RecyclerView mGoodsRv;
    private FabricCheckTaskAdapter mAdapter;
    private List<FabricCheckTask> mDataSource = new ArrayList<>();

    @BindView(R.id.view_searchBar)
    ListBottomBar mBottomView;

    @BindView(R.id.goods_filter)
    CheckFilter mFilterView;

    private LinearLayoutManager mLayoutManager;


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
        mAdapter = new FabricCheckTaskAdapter(R.layout.item_fabric_check_task, mDataSource);
        mAdapter.setOnItemClickListener(this);
        mGoodsRv.setAdapter(mAdapter);
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
        String url = Constant.APP_BASE_URL + "fabricCheckTask/search?keyword=&pageNum=" + mPage + "&pageSize=10" + "&enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&modifyTimeApply=modifyTimeApply&modifyTimeExamine=";
        mIsLoading = true;
        Log.e("net666", url);
        mVolleyUtil.httpGetRequest(url, response -> {
            Log.e("net666", response);
            mDialog.hide();
            mIsLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            PageInfo<FabricCheckTask> fabricCheckTaskPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<FabricCheckTask>>() {
            });
            List<FabricCheckTask> fabricCheckTaskList = fabricCheckTaskPageInfo.getList();
            if (fabricCheckTaskList == null) {
                return;
            }
            if (mPage == 1) {
                mAdapter.setNewData(fabricCheckTaskList);
            } else {
                mAdapter.addData(fabricCheckTaskList);
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


    /**
     * 点击底部加号
     */
    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(this, SelectOrderActivity.class);
        intent.putExtra("request", REQUEST_FABRIC_CHECK_TASK);
        startActivity(intent);
    }

    /**
     * 点击底部搜索
     */
    @Override
    public void onSearchIconDidClick() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, FabricCheckOrderTaskActivity.class);
        intent.putExtra("data", JSON.toJSONString(mAdapter.getData().get(position)));
        startActivity(intent);
    }
}
