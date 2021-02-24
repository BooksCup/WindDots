package com.wd.winddots.activity.work;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.detail_goods_recycler)
    RecyclerView goodsRecyclerView;

    DetailGoodsInfoAdapter detailGoodsInfoAdapter;

    @BindView(R.id.detail_orders_recycler)
    RecyclerView detailOrdersRecyclerView;

    DetailRelatedOrderAdapter detailRelatedOrderAdapter;

    @BindView(R.id.factory_recycler)
    RecyclerView factoryRecyclerView;

    FactoryAdapter factoryAdapter;


   /* @BindView(R.id.add_pic)
    RecyclerView addRecyclerView;

    PicsAdapter picsAdapter;*/

    @Override
    public int getContentView() {
        return R.layout.activity_delivery_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager1 = new  LinearLayoutManager(this);
        goodsRecyclerView.setLayoutManager(layoutManager1);
        goodsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        detailGoodsInfoAdapter = new DetailGoodsInfoAdapter();
        goodsRecyclerView.setAdapter(detailGoodsInfoAdapter);


        LinearLayoutManager layoutManager2 = new  LinearLayoutManager(this);
        detailOrdersRecyclerView.setLayoutManager(layoutManager2);
        detailOrdersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        detailRelatedOrderAdapter = new DetailRelatedOrderAdapter(this);
        detailOrdersRecyclerView.setAdapter(detailRelatedOrderAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
