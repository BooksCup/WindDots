package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.stock.in.StockInApplyAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.StockInApply;
import com.wd.winddots.enums.StockBizTypeEnum;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

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
 * 入库申请(入库单)
 *
 * @author zhou
 */
public class StockInApplyActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_stock_in_apply)
    RecyclerView mStockInApplyRv;

    @BindView(R.id.srl_stock_in_apply)
    SwipeRefreshLayout mStockInApplySrl;

    @BindView(R.id.iv_add)
    ImageView mAddIv;

    StockInApplyAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<StockInApply> mStockInApplyList = new ArrayList<>();

    // 入库类型选择框
    private PopupWindow mPopupWindow;
    private View mPopupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in_apply);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                initPopupWindow();
                if (!mPopupWindow.isShowing()) {
                    // 以下拉方式显示popupwindow
                    mPopupWindow.showAsDropDown(mAddIv, 0, 0);
                } else {
                    mPopupWindow.dismiss();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        mStockInApplySrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mStockInApplySrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockInApplyRv.setLayoutManager(layoutManager);
        mStockInApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StockInApplyAdapter(this, R.layout.item_stock_in_apply, mStockInApplyList);
        mStockInApplyRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mStockInApplySrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mStockInApplyRv);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Constant.APP_BASE_URL + "stockApplication?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "&stockType=" + Constant.STOCK_TYPE_IN +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mStockInApplySrl.setRefreshing(false);
            PageInfo<StockInApply> stockInApplyPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<StockInApply>>() {
            });
            List<StockInApply> stockInApplyList = stockInApplyPageInfo.getList();

            if (mPage == 1) {
                mStockInApplyList.clear();
            }
            mStockInApplyList.addAll(stockInApplyList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mStockInApplyList.size() >= stockInApplyPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mStockInApplySrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(StockInApplyActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StockInApply stockInApply = mStockInApplyList.get(position);
        if (StockBizTypeEnum.STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN.getType().equals(stockInApply.getBizType())) {
            // 办公用品入库
            Intent intent = new Intent(StockInApplyActivity.this, OfficeSuppliesInApplyDetailActivity.class);
            intent.putExtra("stockInApplyId", stockInApply.getId());
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    /**
     * 初始化首页弹出框
     */
    private void initPopupWindow() {

        mPopupView = View.inflate(this, R.layout.popup_window_stock_in_apply, null);
        mPopupWindow = new PopupWindow();
        // 设置SelectPicPopupWindow的View
        mPopupWindow.setContentView(mPopupView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 刷新状态
        mPopupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);

        // 办公用品入库
        RelativeLayout mOfficeSuppliesRl = mPopupView.findViewById(R.id.rl_office_supplies);
        mOfficeSuppliesRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(StockInApplyActivity.this, AddOfficeSuppliesInActivity.class);
                startActivity(intent);
            }
        });

    }
}
