package com.wd.winddots.mvp.widget;

import android.content.Intent;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.PointsListBean;
import com.wd.winddots.mvp.widget.adapter.rv.PointsAdapter;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PointsActivity extends CommonActivity implements SwipeRefreshLayout.OnRefreshListener{

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mPointsTextView;
    private PointsAdapter mAdapter;
    private ArrayList<PointsListBean> mDataSource = new ArrayList<>();


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter() {
        };
    }


    @Override
    public void initView() {
        super.initView();
        setTitleText("积分");
        addBadyView(R.layout.activity_points);



        mRecyclerView = findViewById(R.id.activity_points_rlist);
        mSwipeRefreshLayout = findViewById(R.id.activity_points_srl);
        mPointsTextView = findViewById(R.id.activity_points_points);

        mSwipeRefreshLayout.setOnRefreshListener(this);


        mAdapter = new PointsAdapter(R.layout.item_points,mDataSource);

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(false);

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        Intent intent = getIntent();
        String points = intent.getStringExtra("points");

        mPointsTextView.setText(points);
    }

    @Override
    public void initData() {
        getData();
        mSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }

    private void getData() {
        compositeSubscription.add(dataManager.getPoints(SpHelper.getInstance(this).getString("id")).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        Gson gson = new Gson();

                        List<PointsListBean> listBeans = gson.fromJson(s,new TypeToken<List<PointsListBean>>(){}.getType());

                        mDataSource.clear();
                        mDataSource.addAll(listBeans);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                );
    }


}
