package com.wd.winddots.desktop.list.employee.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.list.employee.adapter.EmployeeListAdapter;
import com.wd.winddots.desktop.list.employee.bean.EmployeeListBean;
import com.wd.winddots.desktop.list.employee.view.EmployeeListHeader;
import com.wd.winddots.net.ElseDataManager;
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

/**
 * FileName: EmployeeListActivity
 * Author: 郑
 * Date: 2020/11/10 9:12 AM
 * Description: 人员列表
 */
public class EmployeeActivity extends CommonActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private EmployeeListAdapter mAdapter;

    private int page = 1;
    private int pageSize = 10;
    private List<EmployeeListBean.EmployeeItem> dataSource = new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_employee);

        setTitleText(mContext.getString(R.string.employee_title));

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

//        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//        Drawable drawable = getResources().getDrawable(R.drawable.divider_employee);
//        decoration.setDrawable(drawable);
//
//        mRecyclerView.addItemDecoration(decoration);
        mAdapter = new EmployeeListAdapter(R.layout.item_employee,dataSource);
        mRecyclerView.setAdapter(mAdapter);

        if (1 == SpHelper.getInstance(mContext).getInt("userIsSuperAdmin")){
            View view = new EmployeeListHeader(mContext);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserApplyActivity.class);
                    startActivity(intent);
                }
            });
            mAdapter.setHeaderView(view);
        }

    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }


    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        page = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() throws InterruptedException {
        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        page += 1;
        getData();
    }

    private void getData(){
        compositeSubscription.add(dataManager.getEmployeeList(SpHelper.getInstance(mContext).getEnterpriseId(),page,pageSize).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666getEmployeeList", s);
                        Gson gson = new Gson();
                        EmployeeListBean bean = gson.fromJson(s, EmployeeListBean.class);
                        if (page == 1) {
                            dataSource.clear();
                        }
                        dataSource.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.loadMoreComplete();
                        if (dataSource.size() >= bean.getTotalCount()) {
                            mAdapter.setEnableLoadMore(false);
                        }

                    }
                })
        );
    }
}
