package com.wd.winddots.mvp.widget.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.mvp.widget.fragment.BusinessAreaFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: BusinessTapAdapter
 * Author: 郑
 * Date: 2020/6/30 12:22 PM
 * Description:
 */
public class BusinessTapAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"资讯", "商品", "人才"};
    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    public BusinessTapAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = BusinessAreaFragment.newInstance();
            } else if (position == 1){
                return new Fragment();
                //fragment = GroupChatTabFragment.newInstance();
            } else {
                return new Fragment();
                //fragment = ExamineApproveTabFragment.newInstance();
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
