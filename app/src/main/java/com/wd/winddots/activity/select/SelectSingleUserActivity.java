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
import com.wd.winddots.adapter.select.SingleUserAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

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
 * 选择单用户
 *
 * @author zhou
 */
public class SelectSingleUserActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.rv_user)
    RecyclerView mUserRv;

    @BindView(R.id.srl_user)
    SwipeRefreshLayout mUserSrl;

    SingleUserAdapter mSingleUserAdapter;
    VolleyUtil mVolleyUtil;
    List<User> mUserList = new ArrayList<>();
    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_single_user);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUserRv.setLayoutManager(layoutManager);
        mSingleUserAdapter = new SingleUserAdapter(R.layout.item_select_single_user, mUserList);
        mUserRv.setAdapter(mSingleUserAdapter);
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
        mUserSrl.setOnRefreshListener(this);
        mSingleUserAdapter.setOnLoadMoreListener(this, mUserRv);
        mSingleUserAdapter.setOnItemClickListener(this);
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
            mUserSrl.setRefreshing(false);
            PageInfo<User> userPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<User>>() {
            });
            List<User> userList = userPageInfo.getList();

            if (mPage == 1) {
                mUserList.clear();
            }
            mUserList.addAll(userList);
            mSingleUserAdapter.setKeyword(mKeyword);
            mSingleUserAdapter.notifyDataSetChanged();
            mSingleUserAdapter.loadMoreComplete();
            if (mUserList.size() >= userPageInfo.getTotal()) {
                mSingleUserAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mUserSrl.setRefreshing(false);
            mSingleUserAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(SelectSingleUserActivity.this, volleyError);
        });
    }

    @Override
    public void onRefresh() {
        mUserSrl.setRefreshing(true);
        mSingleUserAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mUserSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        User user = mUserList.get(position);
        Intent intent = new Intent();
        intent.putExtra("userId", user.getId());
        intent.putExtra("userName", user.getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}
