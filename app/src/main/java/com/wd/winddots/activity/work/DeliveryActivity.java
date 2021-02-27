package com.wd.winddots.activity.work;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.view.ListBottomBar;
import com.wd.winddots.desktop.view.filter.FilterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,OnRecyclerItemClickListener{

    @BindView(R.id.filterView)
    FilterView mFilterView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.delivery_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.view_searchBar)
    ListBottomBar mSearchBarView;

    DeliveryAdapter mAdapter;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    private List<DeliveryBean> deliveryBeanList;

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() throws InterruptedException {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_delivery;
    }



    @Override
    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        deliveryBeanList = new ArrayList<>();
        DeliveryBean deliveryBean1 = new DeliveryBean();
        deliveryBean1.deliveryId = "111";
        deliveryBean1.deliveryName = "定制上衣（200）";
        deliveryBean1.iconUrl = "";
        deliveryBean1.deliveryType = "销售出库";
        deliveryBean1.applicant = "周楠";
        deliveryBean1.company = "自营服装部";
        deliveryBean1.applyDate = "2021-02-25";
        deliveryBean1.isCheck = true;

        deliveryBeanList.add(deliveryBean1);

        DeliveryBean deliveryBean2 = new DeliveryBean();
        deliveryBean2.deliveryId = "222";
        deliveryBean2.deliveryName = "定制裤子（20）";
        deliveryBean2.iconUrl = "";
        deliveryBean2.deliveryType = "销售出库";
        deliveryBean2.applicant = "老王";
        deliveryBean2.company = "GC自营服装部";
        deliveryBean2.applyDate = "2021-02-26";
        deliveryBean2.isCheck = false;

        deliveryBeanList.add(deliveryBean2);

        mAdapter = new DeliveryAdapter(this,deliveryBeanList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        DeliveryBean bean = deliveryBeanList.get(position);
        Intent intent;
        if (bean.isCheck){
            intent = new Intent(DeliveryActivity.this,DeliveryDetailActivity.class);
        }else {
            intent = new Intent(DeliveryActivity.this,AddDeliveryActivity.class);
        }
        startActivity(intent);
    }
}
