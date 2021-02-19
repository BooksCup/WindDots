package com.wd.winddots.mvp.widget.fragment;


import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.mvp.presenter.impl.CompanyPresenterImpl;
import com.wd.winddots.mvp.view.CompanyView;

public class CompanyTabFragment extends BaseFragment<CompanyView, CompanyPresenterImpl> {
    @Override
    public CompanyPresenterImpl initPresenter() {
        return new CompanyPresenterImpl();
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_tab_company;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
