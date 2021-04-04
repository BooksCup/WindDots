package com.wd.winddots.mvp.widget.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.fragment.MineInfoFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 我
 *
 * @author zhou
 */
public class MineTabAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"我"};

    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    public MineTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null) {
            BaseFragment fragment = null;
            if (position == 0) {
                fragment = MineInfoFragment.newInstance();
            }
            mBaseFragments[position] = fragment;
        }
        return mBaseFragments[position];
    }

    @Override
    public int getCount() {
        return homeTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return homeTitles[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
