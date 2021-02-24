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
import com.wd.winddots.adapter.select.GoodsAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.PageInfo;
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
 * 选择物品
 *
 * @author zhou
 */
public class SelectGoodsActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_goods)
    RecyclerView mGoodsRv;

    @BindView(R.id.srl_goods)
    SwipeRefreshLayout mGoodsSrl;

    @BindView(R.id.et_search)
    EditText mSearchEt;

    GoodsAdapter mGoodsAdapter;
    VolleyUtil mVolleyUtil;
    List<Goods> mGoodsList = new ArrayList<>();

    int mPage = 1;
    int mPageSize = 10;
    String mKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goods);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mGoodsRv.setLayoutManager(layoutManager);
        mGoodsRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mGoodsAdapter = new GoodsAdapter(R.layout.item_select_goods, mGoodsList);
        mGoodsRv.setAdapter(mGoodsAdapter);
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
        mGoodsSrl.setOnRefreshListener(this);
        mGoodsAdapter.setOnLoadMoreListener(this, mGoodsRv);
        mGoodsAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        mGoodsSrl.setRefreshing(true);
        mGoodsAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mGoodsSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final Goods goods = mGoodsList.get(position);
        Intent intent = new Intent();
        intent.putExtra("goodsId", goods.getId());
        intent.putExtra("goodsName", goods.getGoodsName());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getData() {
        String url;
        try {
            url = Constant.APP_BASE_URL + "goods/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage
                    + "&pageSize=" + mPageSize
                    + "&keyword=" + URLEncoder.encode(mKeyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "goods/search?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId()
                    + "&pageNum=" + mPage
                    + "&pageSize=" + mPageSize
                    + "&keyword=" + mKeyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mGoodsSrl.setRefreshing(false);
            PageInfo<Goods> goodsPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Goods>>() {
            });
            List<Goods> goodsList = goodsPageInfo.getList();

            if (mPage == 1) {
                mGoodsList.clear();
            }
            mGoodsList.addAll(goodsList);
            mGoodsAdapter.setKeyword(mKeyword);
            mGoodsAdapter.notifyDataSetChanged();
            mGoodsAdapter.loadMoreComplete();
            if (mGoodsList.size() >= goodsPageInfo.getTotal()) {
                mGoodsAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mGoodsSrl.setRefreshing(false);
            mGoodsAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(SelectGoodsActivity.this, volleyError);
        });
    }

}