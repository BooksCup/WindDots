package com.wd.winddots.activity.check.fabric;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wd.winddots.R;
import com.wd.winddots.adapter.check.fabric.FabricCheckProcessAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        implements View.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener,
        FabricCheckProcessAdapter.OnSubItemClickListener{

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_goods)
    PinnedHeaderRecyclerView mGoodsRv;

    private LinearLayoutManager mLayoutManager;
    private FabricCheckProcessAdapter mAdapter;

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
        mGoodsRv.setOnScrollChangeListener(this);
        mAdapter = new FabricCheckProcessAdapter(this);
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


    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (mIsLoading || mEndLoading) {
            return;
        }
        getLastVisiblePosition();
    }


    @Override
    public void onItemClick(CheckGoodsBean.CheckGang gang) {

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
