package com.wd.winddots.mvp.widget.fragment;

import com.google.android.material.tabs.TabLayout;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.mvp.presenter.impl.BusinessPresenterImpl;
import com.wd.winddots.mvp.widget.adapter.BusinessTapAdapter;

import androidx.viewpager.widget.ViewPager;

/**
 * FileName: BusinessFragment
 * Author: 郑
 * Date: 2020/6/30 12:20 PM
 * Description:
 */
public class BusinessFragment extends BaseFragment {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public BasePresenter initPresenter() {
        return new BusinessPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_business;
    }

    @Override
    public void initView() {
        mTabLayout = ((TabLayout) mView.findViewById(R.id.fragment_message_tablayout));
        mViewPager = ((ViewPager) mView.findViewById(R.id.fragment_message_viewpager));
//
        BusinessTapAdapter adapter = new BusinessTapAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
