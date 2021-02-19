package com.wd.winddots.fast.fragment;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.fast.activity.MineAttendanceAddActivity;
import com.wd.winddots.fast.activity.MineAttendanceDetailActivity;
import com.wd.winddots.fast.adapter.MineAttendanceAdapter;
import com.wd.winddots.fast.bean.MineAttendanceListBean;
import com.wd.winddots.fast.view.MineAttendanceHeader;
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
 * FileName: MineAttendanceFrament
 * Author: 郑
 * Date: 2020/5/26 3:27 PM
 * Description:
 */
public class MineAttendanceFrament extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        MineAttendanceHeader.MineAttendanceHeaderAddIconListener,
        BaseQuickAdapter.OnItemClickListener{


    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    private String mType;
    private MineAttendanceAdapter mAdapter;
    private int page = 1;
    private int pageSize = 10;


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;




    private List<MineAttendanceListBean.MineAttendanceBean> mDataSource = new ArrayList<>();


    public static BaseFragment newInstance(String type) {
        MineAttendanceFrament frament = new MineAttendanceFrament();
        frament.compositeSubscription = new CompositeSubscription();
        frament.dataManager = new ElseDataManager();
        frament.mType = type;
        return frament;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_attendance;
    }

    @Override
    public void initView() {

        mRecyclerView = mView.findViewById(R.id.fragemnt_mine_attendance_rlist);
        mSwipeRefreshLayout = mView.findViewById(R.id.fragemnt_mine_attendance_srfl);


        mAdapter = new MineAttendanceAdapter(R.layout.item_mine_attendance, mDataSource,mType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        MineAttendanceHeader header = new MineAttendanceHeader(mContext);
        header.setMineAttendanceHeaderAddIconListener(this);
        mAdapter.setHeaderView(header);
       // mAdapter.setMineAttendanceAdapterOnItemClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();

    }

    private void getData() {

        //showToast(String.valueOf(page));
        compositeSubscription.add(dataManager.getMineAttendApply(SpHelper.getInstance(mContext).getUserId(), mType, page, pageSize).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        MineAttendanceListBean bean = gson.fromJson(s, MineAttendanceListBean.class);
                        if (page == 1){
                            mDataSource.clear();
                        }
                        if (mDataSource.size() + bean.getList().size() <= bean.getTotalCount()){
                            mDataSource.addAll(bean.getList());
                        }

                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreComplete();
                        if (mDataSource.size() >= bean.getTotalCount()) {
                            mAdapter.setEnableLoadMore(false);
                        }
                    }
                })
        );
    }

    @Override
    public void onLoadMoreRequested() {
        page +=1;
        getData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        getData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        onRefresh();
    }

    @Override
    public void addIconDidClick() {
        Intent intent = new Intent(mContext, MineAttendanceAddActivity.class);
        intent.putExtra("type",mType);
        startActivityForResult(intent,1);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String id = mDataSource.get(position).getId();
        Intent intent = new Intent(mContext, MineAttendanceDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("type",mType);
        startActivityForResult(intent,1);
    }





}
