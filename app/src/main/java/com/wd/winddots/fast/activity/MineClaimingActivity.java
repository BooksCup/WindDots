package com.wd.winddots.fast.activity;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;

import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.fast.adapter.MineClaimingAdapter;

import com.wd.winddots.fast.bean.ClaimingListBean;
import com.wd.winddots.fast.presenter.impl.MineClaimingPresenterImpl;
import com.wd.winddots.fast.presenter.view.MineClaimingView;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * FileName: MineClaimingActivity
 * Author: 郑
 * Date: 2020/5/4 4:37 PM
 * Description: 我的报销列表
 */
public class MineClaimingActivity extends CommonActivity
        <MineClaimingView, MineClaimingPresenterImpl>
        implements MineClaimingView,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    private final int REQUEST_CODE_DETAIL = 100; //跳转详情
    private final int REQUEST_CODE_ADD = 200; //跳转新增页面


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private MineClaimingAdapter mAdapter;
    private List<ClaimingListBean.ListBean> mDataSource = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;

    @Override
    public MineClaimingPresenterImpl initPresenter() {
        return new MineClaimingPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setTitleText("报销");
        addBadyView(R.layout.activity_mine_claiming);

        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.icon_bottom_add);

        mSwipeRefreshLayout = findViewById(R.id.activity_mine_claiming_srl);
        mRecyclerView = findViewById(R.id.activity_mine_claiming_rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MineClaimingAdapter(R.layout.item_mine_claiming, mDataSource);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExpenseAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        presenter.getMineClaimingList(SpHelper.getInstance(mContext).getUserId(), page, pageSize);
        mSwipeRefreshLayout.setRefreshing(true);
    }


    /*
     * 加载数据
     * */
    @Override
    public void onRefresh() {
        page = 1;
        presenter.getMineClaimingList(SpHelper.getInstance(mContext).getUserId(), page, pageSize);
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        page += 1;
        presenter.getMineClaimingList(SpHelper.getInstance(mContext).getUserId(), page, pageSize);


    }


    /*
     * 获取列表数据
     * */
    @Override
    public void getClaimingListSuccess(ClaimingListBean bean) {
        if (page == 1) {
            mDataSource.clear();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mDataSource.addAll(bean.getList());
        mAdapter.notifyDataSetChanged();
        mAdapter.loadMoreComplete();

        if (mDataSource.size() >= bean.getTotalCount()) {
            mAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void getClaimingListError(String error) {
        showToast("获取失败,请稍后重试");
    }

    @Override
    public void getClaimingListCompleted() {
        mAdapter.loadMoreComplete();
    }


    /*
     * item 点击事件
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ClaimingListBean.ListBean item = mDataSource.get(position);
        Intent intent = new Intent(mContext, MineClaimingDetailActivity.class);
        intent.putExtra("id", item.getId());
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        onRefresh();
    }
}
