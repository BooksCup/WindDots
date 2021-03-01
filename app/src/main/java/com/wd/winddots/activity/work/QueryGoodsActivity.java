package com.wd.winddots.activity.work;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QueryGoodsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.goods_type_recycler)
    RecyclerView typeRecyclerView;

    GoodsTypeAdapter typeAdapter;


    @BindView(R.id.goods_recycler)
    RecyclerView goodsRecyclerView;

    QueryGoodsAdapter queryGoodsAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_query_goods;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager1 = new  LinearLayoutManager(this);
        typeRecyclerView.setLayoutManager(layoutManager1);
        typeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
         typeAdapter= new GoodsTypeAdapter();
        typeRecyclerView.setAdapter(typeAdapter);

        LinearLayoutManager layoutManager2 = new  LinearLayoutManager(this);
        goodsRecyclerView.setLayoutManager(layoutManager2);
        goodsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        queryGoodsAdapter= new QueryGoodsAdapter();
        goodsRecyclerView.setAdapter(queryGoodsAdapter);
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryGoodsActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
