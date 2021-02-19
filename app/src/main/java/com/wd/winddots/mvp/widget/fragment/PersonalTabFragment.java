package com.wd.winddots.mvp.widget.fragment;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.mvp.presenter.impl.PersonalPresenterImpl;
import com.wd.winddots.mvp.view.PersonalView;

public class PersonalTabFragment extends BaseFragment<PersonalView, PersonalPresenterImpl> {
    @Override
    public PersonalPresenterImpl initPresenter() {
        return new PersonalPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_personal;
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
