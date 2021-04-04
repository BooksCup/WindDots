package com.wd.winddots.activity.contract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.contract.LinkmanAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Linkman;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系人管理
 */
public class LinkmanActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_linkman)
    RecyclerView mLinkmanRv;

    @BindView(R.id.srl_linkman)
    SwipeRefreshLayout mLinkmanSrl;

    int mPage = 1;
    int mPageSize = 10;
    VolleyUtil mVolleyUtil;
    LinkmanAdapter mAdapter;
    List<Linkman> mLinkmanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkman);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLinkmanRv.setLayoutManager(layoutManager);
        mLinkmanRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new LinkmanAdapter(this, R.layout.item_linkman, mLinkmanList);
        mLinkmanRv.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        String url = null;
        try {
            url = Constant.APP_BASE_URL +
                    "linkman?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                    "&pageNum=" + mPage +
                    "&pageSize=" + mPageSize;
        } catch (Exception e) {
            e.printStackTrace();
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mLinkmanSrl.setRefreshing(false);
            PageInfo<Linkman> linkmanPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Linkman>>() {
            });
            List<Linkman> linkmanList = linkmanPageInfo.getList();

            if (mPage == 1) {
                mLinkmanList.clear();
            }
            mLinkmanList.addAll(linkmanList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mLinkmanList.size() >= linkmanPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mLinkmanSrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });

    }

    private void initListener() {
        mLinkmanSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mLinkmanRv);
        mAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                // 新建联系人
                Intent intent = new Intent(this, AddLinkmanActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    @Override
    public void onRefresh() {
        mLinkmanSrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mLinkmanSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}