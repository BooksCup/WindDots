package com.wd.winddots.mvp.widget.fragment.vp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.bean.resp.ExamineApproveBean;
import com.wd.winddots.mvp.presenter.impl.ExamineApprovePresenterImpl;
import com.wd.winddots.mvp.view.ExamineApproveView;
import com.wd.winddots.mvp.widget.ApprovalProcessActivity;
import com.wd.winddots.mvp.widget.adapter.rv.ExamineApproveAdapter;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

public class ExamineApproveTabFragment extends BaseFragment<ExamineApproveView, ExamineApprovePresenterImpl> implements ExamineApproveView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<ExamineApproveBean.ListBean> mListBeanList = new ArrayList<>();
    private ExamineApproveAdapter mAdapter;

    private int page = 1;
    private View mFooterView;

    public static ExamineApproveTabFragment newInstance() {
        ExamineApproveTabFragment fragment = new ExamineApproveTabFragment();
        return fragment;
    }

    @Override
    public ExamineApprovePresenterImpl initPresenter() {
        return new ExamineApprovePresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_examineapprove;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = ((SwipeRefreshLayout) mView.findViewById(R.id.fragment_tab_examineapprove_srl));
        mRecyclerView = ((RecyclerView) mView.findViewById(R.id.fragment_tab_examineapprove_rv));

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new ExamineApproveAdapter(R.layout.item_examineapprove,mListBeanList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.refreshExamineApproveListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        presenter.refreshExamineApproveListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.loadMoreExamineApproveListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"),page);
    }


    @Override
    public void onExamineApproveListRefreshSuccess(ExamineApproveBean bean) {
        List<ExamineApproveBean.ListBean> list = bean.getList();
        if (list.size() > 0){
            mListBeanList.clear();
            mListBeanList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            showToast("暂无任何数据!");
        }

        if (list.size() < 10){
            mAdapter.setEnableLoadMore(false);
            if (mFooterView == null){
                mFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_no_more_data,null,false);
                mAdapter.addFooterView(mFooterView);
            }
        }

    }

    @Override
    public void onExamineApproveListRefreshError(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onExamineApproveListRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onExamineApproveListLoadMoreSuccess(ExamineApproveBean bean) {
        List<ExamineApproveBean.ListBean> dataList = bean.getList();
        if (dataList.size() != 0){
            mListBeanList.addAll(dataList);
            mAdapter.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        }

        if (dataList.size() < 10){
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onExamineApproveListLoadMoreError(String errorMsg) {
        mAdapter.loadMoreFail();
        page--;
    }

    @Override
    public void onExamineApproveListLoadMoreComplete() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ExamineApproveBean.ListBean listBean = mListBeanList.get(position);
        String id = listBean.getId();

        String recallType = listBean.getRecallType();
        String userType = listBean.getUserType();
        String approvalStatus = listBean.getApprovalStatus();
        Intent intent = new Intent();
        intent.putExtra("position",position);
        intent.putExtra("id",id);
        intent.putExtra("type",listBean.getType());
        intent.putExtra("recallType",recallType);
        intent.putExtra("userType",userType);
        intent.putExtra("approvalStatus",approvalStatus);
        intent.setClass(mContext,ApprovalProcessActivity.class);
        startActivityForResult(intent,101);
    }


    /**
     * 接收审核流程详情 更新界面的最新数据与状态
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null){
            int position = data.getIntExtra("position", 0);
            String status = data.getStringExtra("status");

            List<ExamineApproveBean.ListBean> list = mAdapter.getData();
            list.get(position).setApprovalStatus(status);
            mAdapter.notifyItemChanged(position);
        }
    }
}
