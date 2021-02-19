package com.wd.winddots.mvp.widget.fragment;

import androidx.viewpager.widget.ViewPager;
import rx.subscriptions.CompositeSubscription;

import com.google.android.material.tabs.TabLayout;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.mvp.presenter.impl.DesktopPresenterImpl;
import com.wd.winddots.mvp.view.DesktopView;
import com.wd.winddots.desktop.adapter.DesktopTapAdapter;
import com.wd.winddots.net.ElseDataManager;

public class DesktopFragment extends BaseFragment<DesktopView, DesktopPresenterImpl> {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public final CompositeSubscription compositeSubscription;
    public final ElseDataManager dataManager;

    public DesktopFragment() {
        this.compositeSubscription = new CompositeSubscription();
        this.dataManager = new ElseDataManager();
    }

    @Override
    public DesktopPresenterImpl initPresenter() {
        return new DesktopPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_desktop;
    }

    @Override
    public void initView() {
        mTabLayout = ((TabLayout) mView.findViewById(R.id.fragment_desktop_tablayout));
        mViewPager = ((ViewPager) mView.findViewById(R.id.fragment_desktop_viewpager));


        DesktopTapAdapter tapAdapter = new DesktopTapAdapter(getChildFragmentManager());
        mViewPager.setAdapter(tapAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setCurrentItem(0);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }
}
