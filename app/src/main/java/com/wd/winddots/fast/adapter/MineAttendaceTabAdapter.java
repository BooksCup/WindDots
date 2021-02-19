package com.wd.winddots.fast.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.fast.fragment.MineAttendanceFrament;
import com.wd.winddots.fast.fragment.MineAttendanceSiginFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: MineAttendaceTabAdapter
 * Author: 郑
 * Date: 2020/5/26 9:38 AM
 * Description:
 */
public class MineAttendaceTabAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"考勤", "请假", "加班","公出"};

    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    public MineAttendaceTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = MineAttendanceSiginFragment.newInstance();
            } else if (position == 1){
                fragment = MineAttendanceFrament.newInstance("0");
            } else if (position == 2){
                fragment = MineAttendanceFrament.newInstance("1");
            } else {
                fragment = MineAttendanceFrament.newInstance("11");
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

    // TODO: 2020/04/07  删除父类方法 防止销毁之前的pager
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
