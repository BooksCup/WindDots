package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.select.WareHouseAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.WareHouse;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择仓库
 *
 * @author zhou
 */
public class SelectWareHouseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.rv_ware_house)
    RecyclerView mWareHouseRv;

    @BindView(R.id.srl_ware_house)
    SwipeRefreshLayout mWareHouseSrl;

    WareHouseAdapter mWareHouseAdapter;
    VolleyUtil mVolleyUtil;
    List<WareHouse> mWareHouseList = new ArrayList<>();
    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ware_house);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWareHouseRv.setLayoutManager(layoutManager);
        mWareHouseRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mWareHouseAdapter = new WareHouseAdapter(R.layout.item_select_ware_house, mWareHouseList);
        mWareHouseRv.setAdapter(mWareHouseAdapter);
        getData();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                showLoadingDialog();
                mKeyword = mSearchEt.getText().toString();
                mPage = 1;
                hideKeyboard();
                getData();
                break;
        }
    }

    public void initListener() {
        mWareHouseSrl.setOnRefreshListener(this);
        mWareHouseAdapter.setOnLoadMoreListener(this, mWareHouseRv);
        mWareHouseAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "wareHouse?enterpriseId="
                    + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage
                    + "&pageSize=" + mPageSize
                    + "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "wareHouse?enterpriseId="
                    + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage
                    + "&pageSize=" + mPageSize
                    + "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mWareHouseSrl.setRefreshing(false);
            PageInfo<WareHouse> wareHousePageInfo = JSON.parseObject(response, new TypeReference<PageInfo<WareHouse>>() {
            });
            List<WareHouse> wareHouseList = wareHousePageInfo.getList();

            if (mPage == 1) {
                mWareHouseList.clear();
            }
            mWareHouseList.addAll(wareHouseList);
            mWareHouseAdapter.setKeyword(mKeyword);
            mWareHouseAdapter.notifyDataSetChanged();
            mWareHouseAdapter.loadMoreComplete();
            if (mWareHouseList.size() >= wareHousePageInfo.getTotal()) {
                mWareHouseAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mWareHouseSrl.setRefreshing(false);
            mWareHouseAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(SelectWareHouseActivity.this, volleyError);
        });
    }

    @Override
    public void onRefresh() {
        mWareHouseSrl.setRefreshing(true);
        mWareHouseAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mWareHouseSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        WareHouse wareHouse = mWareHouseList.get(position);
        Intent intent = new Intent();
        intent.putExtra("wareHouse", wareHouse);
        setResult(RESULT_OK, intent);
        finish();
    }
}
