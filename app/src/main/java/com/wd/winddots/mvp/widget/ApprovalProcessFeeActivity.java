package com.wd.winddots.mvp.widget;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.ApprovalProcessBean;
import com.wd.winddots.mvp.presenter.impl.ApprovalProcessFeePresenterImpl;
import com.wd.winddots.mvp.view.ApprovalProcessFeeView;
import com.wd.winddots.mvp.widget.adapter.rv.ApprovalProcessFeeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessFeeActivity extends CommonActivity<ApprovalProcessFeeView, ApprovalProcessFeePresenterImpl> implements ApprovalProcessFeeView {

    private RecyclerView mRecyclerView;
    private List<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean> mAddressesBeanList = new ArrayList<>();
    private ApprovalProcessFeeAdapter mAdapter;

    private ApprovalProcessBean.MoneyData.DetailListBean detailListBean  = new ApprovalProcessBean.MoneyData.DetailListBean();
    private String mCurrency;

    @Override
    public ApprovalProcessFeePresenterImpl initPresenter() {
        return new ApprovalProcessFeePresenterImpl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_approvalpocessfee);
//        setTitleText(detailListBean.getCostName());
        setTitleText("费用");


        Intent intent = getIntent();
        if (intent != null){
            mCurrency = intent.getStringExtra("currency");
        }


        mRecyclerView = ((RecyclerView) findViewById(R.id.activity_approvalprocessfee_rv));

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(manager);

        mAdapter = new ApprovalProcessFeeAdapter(this,R.layout.item_approvalprocessfee,mAddressesBeanList);
        mRecyclerView.setAdapter(mAdapter);


        addHeaderView();
    }

    private void addHeaderView() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_approvalprocess_fee, null, false);
        TextView tvHeaderMoney = (TextView) headerView.findViewById(R.id.header_approvalprocess_fee_money_tv);
        TextView tvHeaderNote = (TextView) headerView.findViewById(R.id.header_approvalprocess_fee_note_tv);
        tvHeaderMoney.setText(detailListBean.getAmount() + "人民币");
        tvHeaderNote.setText(detailListBean.getRemark());
        mAdapter.addHeaderView(headerView);
    }


    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initData() {
        super.initData();
    }


    //获取币种
    public String getCurrency(){
        return mCurrency;
    }


    /**
     * 接收传递过来的数据
     * @param detailListBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetDetailListBean(ApprovalProcessBean.MoneyData.DetailListBean detailListBean) {
        mAddressesBeanList.addAll(detailListBean.getAddresses());
        this.detailListBean = detailListBean;
        EventBus.getDefault().removeStickyEvent(detailListBean);

    }
}
