package com.wd.winddots.desktop.list.enterprise.fragment;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.enterprise.adapter.EnterpriseLawSuitAdapter;
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
 * Description: 法律诉讼
 */
public class EnterpriseLitigationFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private EnterpriseLawSuitAdapter mAdapter;
    private List<EnterpriseDetailBean.CompanyLawSuitItem> mDataSource = new ArrayList<>();

    public void setData(EnterpriseDetailBean enterpriseDetailBean) {
        mDataSource.clear();
        mDataSource.addAll(enterpriseDetailBean.getTycCompanyLawSuitList());
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public static BaseFragment newInstance() {
        EnterpriseLitigationFragment fragment = new EnterpriseLitigationFragment();
        return fragment;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_enterprise_litigation;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.rlist);
        mAdapter = new EnterpriseLawSuitAdapter(R.layout.item_enterprise_litigation,mDataSource);
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
