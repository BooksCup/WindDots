package com.wd.winddots.activity.stock.out;

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
import com.wd.winddots.adapter.stock.out.StockOutApplyAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.PageInfo;
import com.wd.winddots.entity.StockOutApply;
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
 * 出库申请(出库单)
 *
 * @author zhou
 */
public class StockOutApplyActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_stock_out_apply)
    RecyclerView mStockOutApplyRv;

    @BindView(R.id.srl_stock_out_apply)
    SwipeRefreshLayout mStockOutApplySrl;

    @BindView(R.id.iv_add)
    ImageView mAddIv;

    StockOutApplyAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<StockOutApply> mStockOutApplyList = new ArrayList<>();

    // 入库类型选择框
    private PopupWindow mPopupWindow;
    private View mPopupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out_apply);
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
        mStockOutApplySrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mStockOutApplySrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockOutApplyRv.setLayoutManager(layoutManager);
        mStockOutApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new StockOutApplyAdapter(this, R.layout.item_stock_out_apply, mStockOutApplyList);
        mStockOutApplyRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mStockOutApplySrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mStockOutApplyRv);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Constant.APP_BASE_URL + "stockApplication?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "&stockType=" + Constant.STOCK_TYPE_OUT +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mStockOutApplySrl.setRefreshing(false);
            PageInfo<StockOutApply> stockOutApplyPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<StockOutApply>>() {
            });
            List<StockOutApply> stockOutApplyList = stockOutApplyPageInfo.getList();

            if (mPage == 1) {
                mStockOutApplyList.clear();
            }
            mStockOutApplyList.addAll(stockOutApplyList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mStockOutApplyList.size() >= stockOutApplyPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mStockOutApplySrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(StockOutApplyActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StockOutApply stockOutApply = mStockOutApplyList.get(position);
        if (StockBizTypeEnum.STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN.getType().equals(stockOutApply.getBizType())) {
            // 办公用品领用
//            Intent intent = new Intent(StockOutApplyActivity.this, OfficeSuppliesInApplyDetailActivity.class);
//            intent.putExtra("stockInApplyId", stockOutApply.getId());
//            startActivity(intent);
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

        mPopupView = View.inflate(this, R.layout.popup_window_stock_out_apply, null);
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
                Intent intent = new Intent(StockOutApplyActivity.this, AddOfficeSuppliesOutActivity.class);
                startActivity(intent);
            }
        });

    }
}
