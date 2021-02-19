package com.wd.winddots.mvp.widget.adapter.vp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.message.fragment.ExamineApproveTabFragment;
import com.wd.winddots.message.fragment.GroupChatTabFragment;
import com.wd.winddots.message.fragment.PrivateChatTabFragment;

public class MessageTabAdapter extends FragmentPagerAdapter {
    private String[] homeTitles = new String[]{"私聊", "群聊", "审批"};

    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];
    public MessageTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = PrivateChatTabFragment.newInstance();
            } else if (position == 1){
                fragment = GroupChatTabFragment.newInstance();
            } else {
                fragment = ExamineApproveTabFragment.newInstance();
            }
            mBaseFragments[position] = fragment;
        }
        return mBaseFragments[position];
    }

    @Override
    public int getCount() {
        return homeTitles.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return homeTitles[position];
//    }

    // TODO: 2020/04/07  删除父类方法 防止销毁之前的pager
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
