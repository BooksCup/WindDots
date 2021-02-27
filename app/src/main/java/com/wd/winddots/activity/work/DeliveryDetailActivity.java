package com.wd.winddots.activity.work;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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

    @BindView(R.id.iv_contact_add)
    ImageView ivContactAdd;


    @BindView(R.id.iv_reviewer_add)
    ImageView ivReviewerAdd;



    @BindView(R.id.detail_pics)
    RecyclerView picsRecyclerView;

    PicsAdapter picsAdapter;

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

        LinearLayoutManager layoutManager3 = new  LinearLayoutManager(this);
        factoryRecyclerView.setLayoutManager(layoutManager3);
        factoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        factoryAdapter = new FactoryAdapter(this);
        factoryRecyclerView.setAdapter(factoryAdapter);

        ivContactAdd.setVisibility(View.GONE);
        ivReviewerAdd.setVisibility(View.GONE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        picsRecyclerView.setLayoutManager(gridLayoutManager);
        picsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        picsAdapter = new PicsAdapter();
        picsRecyclerView.setAdapter(picsAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
