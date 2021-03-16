package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.adapter.check.fabric.FabricCheckProcessAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 面料盘点任务处理
 *
 * @author zhou
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FabricCheckProcessActivity  extends FragmentActivity
        implements  SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_goods)
    RecyclerView mGoodsRv;
    private FabricCheckProcessAdapter mAdapter;
    private List<FabricCheckTask> mDataSource = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;



    private LoadingDialog mDialog;

    private boolean mIsLoading = false;
    private boolean mEndLoading = false;
    private int mPage = 1;

    private String mSearchKey = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_process);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        mDialog = LoadingDialog.getInstance(this);

        initView();
        getData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initView(){
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mGoodsRv.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mGoodsRv.addItemDecoration(new PinnedHeaderItemDecoration());
        mAdapter = new FabricCheckProcessAdapter(R.layout.item_fabric_check_task, mDataSource);
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


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPage = 1;
        mEndLoading = false;
        getData();
    }


    private void getData(){
        if (mEndLoading) {
            return;
        }
        String url = Constant.APP_BASE_URL + "fabricCheckTask/search?keyword=&pageNum=" + mPage + "&pageSize=10" + "&enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() + "&modifyTimeApply=&modifyTimeExamine=modifyTimeExamine";
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, FabricCheckOrderProcessActivity.class);
        intent.putExtra("data", JSON.toJSONString(mAdapter.getData().get(position)));
        startActivity(intent);
    }
}
