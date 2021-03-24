package com.wd.winddots.activity.contract;

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
import com.wd.winddots.adapter.contract.ContractAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Contract;
import com.wd.winddots.entity.PageInfo;
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
 * 合同
 *
 * @author zhou
 */
public class ContractActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_contract)
    RecyclerView mContractRv;

    @BindView(R.id.srl_contract)
    SwipeRefreshLayout mContractSrl;

    @BindView(R.id.iv_add)
    ImageView mAddIv;

    ContractAdapter mAdapter;

    VolleyUtil mVolleyUtil;

    int mPage = 1;
    int mPageSize = 10;
    List<Contract> mContractList = new ArrayList<>();

    // 入库类型选择框
    private PopupWindow mPopupWindow;
    private View mPopupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
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
        mContractSrl.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mContractSrl.isRefreshing()) {
            return;
        }
        mPage += 1;
        getData();
    }

    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mContractRv.setLayoutManager(layoutManager);
        mContractRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ContractAdapter(this, R.layout.item_contract, mContractList);
        mContractRv.setAdapter(mAdapter);
        getData();
    }

    public void initListener() {
        mContractSrl.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mContractRv);
        mAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Constant.APP_BASE_URL +
//                "contract?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId() +
                "contract/search?enterpriseId=" + "1" +
                "&pageNum=" + mPage +
                "&pageSize=" + mPageSize;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            mContractSrl.setRefreshing(false);
            PageInfo<Contract> contractPageInfo = JSON.parseObject(response, new TypeReference<PageInfo<Contract>>() {
            });
            List<Contract> contractList = contractPageInfo.getList();

            if (mPage == 1) {
                mContractList.clear();
            }
            mContractList.addAll(contractList);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
            if (mContractList.size() >= contractPageInfo.getTotal()) {
                mAdapter.setEnableLoadMore(false);
            }

        }, volleyError -> {
            hideLoadingDialog();
            mContractSrl.setRefreshing(false);
            mAdapter.loadMoreComplete();
            mVolleyUtil.handleCommonErrorResponse(ContractActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    /**
     * 初始化弹出框
     */
    private void initPopupWindow() {

        mPopupView = View.inflate(this, R.layout.popup_window_contract, null);
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

        // 实名认证
        RelativeLayout mRealNameCertRl = mPopupView.findViewById(R.id.rl_real_name_cert);
        mRealNameCertRl.setOnClickListener(v -> {
            Intent intent = new Intent(ContractActivity.this, RealNameCertActivity.class);
            startActivity(intent);
        });
    }

}
