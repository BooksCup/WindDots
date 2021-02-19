package com.wd.winddots.desktop.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.fragment.MineInfoFragment;
import com.wd.winddots.desktop.fragment.WorkFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DesktopTapAdapter  extends FragmentPagerAdapter {

    private String[] desktopTitles = new String[]{"泽冠"};
    private BaseFragment[] mBaseFragments = new BaseFragment[desktopTitles.length];

    public DesktopTapAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = WorkFragment.newInstance();
            }
//            else {
//                fragment = MineInfoFragment.newInstance();
//            }
            mBaseFragments[position] = fragment;
        }
        return mBaseFragments[position];
    }




    @Override
    public int getCount() {
        return desktopTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return desktopTitles[position];
    }

    // TODO: 2020/04/07  删除父类方法 防止销毁之前的pager
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
