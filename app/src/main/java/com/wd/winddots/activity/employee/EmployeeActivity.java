package com.wd.winddots.activity.employee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.employee.EmployeeAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.employee.EmployeeHeaderView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 员工
 *
 * @author zhou
 */
public class EmployeeActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.srl_employee)
    SwipeRefreshLayout mEmployeeSrl;

    @BindView(R.id.rv_employee)
    RecyclerView mEmployeeRv;

    EmployeeAdapter mAdapter;

    private int mPage = 1;
    private int mPageSize = 10;
    private List<User> mUserList = new ArrayList<>();
    String mKeyword = "";

    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
        initData();
    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mEmployeeRv.setLayoutManager(layoutManager);

        mAdapter = new EmployeeAdapter(R.layout.item_employee, mUserList);
        mEmployeeRv.setAdapter(mAdapter);

        if (Constant.USER_IS_SUPER_ADMIN == SpHelper.getInstance(this).getInt("userIsSuperAdmin")) {
            View header = new EmployeeHeaderView(this);
            header.setOnClickListener(view -> {
                Intent intent = new Intent(EmployeeActivity.this, EmployeeApplyActivity.class);
                startActivity(intent);
            });
            mAdapter.setHeaderView(header);
        }
    }

    public void initListener() {
        mEmployeeSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mEmployeeRv);
    }

    public void initData() {
        getData();
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
        mEmployeeSrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mEmployeeSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "user/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage + "&pageSize=" + mPageSize +
                    "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "user/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage + "&pageSize=" + mPageSize +
                    "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mEmployeeSrl.setRefreshing(false);
            PageInfo<User> userPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<User>>() {
            });
            List<User> userList = userPageInfo.getList();

            if (mPage == 1) {
                mUserList.clear();
            }
            mUserList.addAll(userList);
            mAdapter.setKeyword(mKeyword);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mUserList.size() >= userPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mEmployeeSrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(EmployeeActivity.this, volleyError);
        });
    }
}
