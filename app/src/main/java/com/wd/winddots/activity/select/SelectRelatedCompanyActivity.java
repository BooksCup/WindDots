package com.wd.winddots.activity.select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.select.RelatedCompanyAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.RelatedCompany;
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
 * 选择-往来单位
 *
 * @author zhou
 */
public class SelectRelatedCompanyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_search)
    TextView mSearchTv;

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.rv_related_company)
    RecyclerView mRelatedCompanyRv;

    @BindView(R.id.srl_related_company)
    SwipeRefreshLayout mRelatedCompanySrl;

    RelatedCompanyAdapter mRelatedCompanyAdapter;
    VolleyUtil mVolleyUtil;
    List<RelatedCompany> mRelatedCompanyList = new ArrayList<>();
    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_related_company);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRelatedCompanyRv.setLayoutManager(layoutManager);
        mRelatedCompanyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRelatedCompanyAdapter = new RelatedCompanyAdapter(R.layout.item_select_related_company, mRelatedCompanyList);
        mRelatedCompanyRv.setAdapter(mRelatedCompanyAdapter);
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
        mRelatedCompanySrl.setOnRefreshListener(this);
        mRelatedCompanyAdapter.setOnLoadMoreListener(this, mRelatedCompanyRv);
        mRelatedCompanyAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "relatedCompany/search?enterpriseId=" + "1" + "&pageNum=" + mPage + "&pageSize=" + mPageSize +
                    "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "relatedCompany/search?enterpriseId=" + "1" + "&pageNum=" + mPage + "&pageSize=" + mPageSize +
                    "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mRelatedCompanySrl.setRefreshing(false);
            PageInfo<RelatedCompany> relatedCompanyPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<RelatedCompany>>() {
            });
            List<RelatedCompany> relatedCompanyList = relatedCompanyPageInfo.getList();

            if (mPage == 1) {
                mRelatedCompanyList.clear();
            }
            mRelatedCompanyList.addAll(relatedCompanyList);
            mRelatedCompanyAdapter.setKeyword(mKeyword);
            mRelatedCompanyAdapter.notifyDataSetChanged();
            mRelatedCompanyAdapter.loadMoreComplete();
            if (mRelatedCompanyList.size() >= relatedCompanyPageInfo.getTotal()) {
                mRelatedCompanyAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mRelatedCompanySrl.setRefreshing(false);
            mRelatedCompanyAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(SelectRelatedCompanyActivity.this, volleyError);
        });
    }

    @Override
    public void onRefresh() {
        mRelatedCompanySrl.setRefreshing(true);
        mRelatedCompanyAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mRelatedCompanySrl.isRefreshing()) {
            return;
        }
        mPageSize += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RelatedCompany relatedCompany = mRelatedCompanyList.get(position);
        Intent intent = new Intent();
        intent.putExtra("relatedCompanyName", relatedCompany.getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}
