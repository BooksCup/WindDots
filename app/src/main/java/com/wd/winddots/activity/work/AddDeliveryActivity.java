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

public class AddDeliveryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.goods_recycler)
    RecyclerView goodsRecyclerView;

    GoodsInfoAdapter mAdapter;

    @BindView(R.id.orders_recycler)
    RecyclerView ordersRecyclerView;

    RelatedOrderAdapter relatedOrderAdapter;

    @BindView(R.id.add_pic)
    RecyclerView addRecyclerView;

    PicsAdapter picsAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_add_delivery;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager1 = new  LinearLayoutManager(this);
        goodsRecyclerView.setLayoutManager(layoutManager1);
        goodsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new GoodsInfoAdapter();
        goodsRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager2 = new  LinearLayoutManager(this);
        ordersRecyclerView.setLayoutManager(layoutManager2);
        ordersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        relatedOrderAdapter = new RelatedOrderAdapter(this);
        ordersRecyclerView.setAdapter(relatedOrderAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        addRecyclerView.setLayoutManager(gridLayoutManager);
        addRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        picsAdapter = new PicsAdapter();
        addRecyclerView.setAdapter(picsAdapter);
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDeliveryActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
