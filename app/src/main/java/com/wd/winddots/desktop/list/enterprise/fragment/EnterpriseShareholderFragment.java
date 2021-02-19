package com.wd.winddots.desktop.list.enterprise.fragment;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.enterprise.adapter.EnterpriseListAdapter;
import com.wd.winddots.desktop.list.enterprise.adapter.EnterpriseShareholderAdapter;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseShareholderItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: EnterpriseShareholderFragment
 * Author: 郑
 * Date: 2020/12/23 10:31 AM
 * Description: 企业股东
 */
public class EnterpriseShareholderFragment extends BaseFragment {


    private EnterpriseDetailBean enterpriseDetailBean;



    public void setData(EnterpriseDetailBean enterpriseDetailBean) {
        mDataSource.clear();
        mDataSource.addAll(enterpriseDetailBean.getTycCompanyHolderList());
        mAdapter.notifyDataSetChanged();
    }

    private RecyclerView mRecyclerView;
    private EnterpriseShareholderAdapter mAdapter;
    private List<EnterpriseDetailBean.CompanyHolderItem> mDataSource = new ArrayList<>();

    public static BaseFragment newInstance() {
        EnterpriseShareholderFragment fragment = new EnterpriseShareholderFragment();
        return fragment;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_enterprise_shareholder;
    }

    @Override
    public void initView() {



        mRecyclerView = mView.findViewById(R.id.rlist);
        mAdapter = new EnterpriseShareholderAdapter(R.layout.item_enterprise_shareholder,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
