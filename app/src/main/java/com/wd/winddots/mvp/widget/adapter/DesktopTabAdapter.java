package com.wd.winddots.mvp.widget.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wd.winddots.base.BaseFragment;

public class DesktopTabAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"泽冠", "个人"};

    private BaseFragment[] mBaseFragments = new BaseFragment[mTitles.length];

    public DesktopTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null) {
            BaseFragment baseFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
