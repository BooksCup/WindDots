package com.wd.winddots.mvp.widget.fragment;

import com.google.android.material.tabs.TabLayout;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.mvp.presenter.impl.MessagePresenterImpl;
import com.wd.winddots.mvp.view.MessageView;
import com.wd.winddots.mvp.widget.adapter.MineTabAdapter;

import androidx.viewpager.widget.ViewPager;

/**
 * FileName: MineFragment
 * Author: 郑
 * Date: 2020/9/3 11:15 AM
 * Description:
 */
public class MineFragment extends BaseFragment<MessageView, MessagePresenterImpl> {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public MessagePresenterImpl initPresenter() {
        return new MessagePresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_mine;
    }

    @Override
    public void initView() {
        mTabLayout = ((TabLayout) mView.findViewById(R.id.fragment_mine_tablayout));
        mViewPager = ((ViewPager) mView.findViewById(R.id.fragment_mine_viewpager));
//
        MineTabAdapter adapter = new MineTabAdapter(getChildFragmentManager());
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
