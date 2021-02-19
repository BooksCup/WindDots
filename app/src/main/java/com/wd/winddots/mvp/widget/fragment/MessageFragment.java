package com.wd.winddots.mvp.widget.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.message.fragment.ExamineApproveTabFragment;
import com.wd.winddots.message.fragment.GroupChatTabFragment;
import com.wd.winddots.message.fragment.PrivateChatTabFragment;
import com.wd.winddots.mvp.presenter.impl.MessagePresenterImpl;
import com.wd.winddots.mvp.view.MessageView;
import com.wd.winddots.mvp.widget.adapter.vp.MessageTabAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment<MessageView, MessagePresenterImpl>
        implements PrivateChatTabFragment.PrivateChatTabUnread,
        GroupChatTabFragment.GroupChatTabUnread ,
        ExamineApproveTabFragment.ExamineApproveTabUnread {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] homeTitles = new String[]{"私聊", "群聊", "审批"};
    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];
    private List<TabLayout.Tab> tabList = new ArrayList<>();

    @Override
    public MessagePresenterImpl initPresenter() {
        return new MessagePresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        mTabLayout = ((TabLayout) mView.findViewById(R.id.fragment_message_tablayout));
        mViewPager = ((ViewPager) mView.findViewById(R.id.fragment_message_viewpager));

        for (int x=0;x<homeTitles.length;x++){
            TabLayout.Tab tab = mTabLayout.newTab();
            View inflate = View.inflate(getContext(), R.layout.view_message_tablayout, null);
            TextView textView = inflate.findViewById(R.id.tv_title);
            textView.setText(homeTitles[x]);
            if (x == 0){
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            tab.setCustomView(inflate);
            mTabLayout.addTab(tab);
            tabList.add(tab);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return homeTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                if (mBaseFragments[position] == null){
                    BaseFragment fragment = null;
                    if (position == 0){
                        fragment = PrivateChatTabFragment.newInstance();
                        PrivateChatTabFragment privateChatTabFragment = (PrivateChatTabFragment) fragment;
                        privateChatTabFragment.setPrivateChatTabUnread(MessageFragment.this);
                    } else if (position == 1){
                        fragment = GroupChatTabFragment.newInstance();
                        GroupChatTabFragment groupChatTabFragment = (GroupChatTabFragment) fragment;
                        groupChatTabFragment.setGroupChatTabUnread(MessageFragment.this);
                    } else {
                        fragment = ExamineApproveTabFragment.newInstance();
                        ExamineApproveTabFragment examineApproveTabFragment = (ExamineApproveTabFragment) fragment;
                        examineApproveTabFragment.setExamineApproveTabUnread(MessageFragment.this);
                    }
                    mBaseFragments[position] = fragment;
                }
                Log.e("net666",position + "");
                return mBaseFragments[position];
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                for (int i = 0;i < tabList.size();i++){
                    TabLayout.Tab tab1 = tabList.get(i);
                    if (tab1 == tab){
                        View view = tab.getCustomView();
                        assert view != null;
                        TextView titleTv = view.findViewById(R.id.tv_title);
                        titleTv.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else {
                        View view = tab1.getCustomView();
                        assert view != null;
                        TextView titleTv = view.findViewById(R.id.tv_title);
                        titleTv.setTextColor(getResources().getColor(R.color.colorTextGray));
                    }
                }
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                MessageTabAdapter adapter = new MessageTabAdapter(getChildFragmentManager());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0f, true);
                TabLayout.Tab tab = tabList.get(position);
                mTabLayout.getTabAt(position).select();
                for (int i = 0;i < tabList.size();i++){
                    TabLayout.Tab tab1 = tabList.get(i);
                    if (i == position){
                        View view = tab.getCustomView();
                        assert view != null;
                        TextView titleTv = view.findViewById(R.id.tv_title);
                        titleTv.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else {
                        View view = tab1.getCustomView();
                        assert view != null;
                        TextView titleTv = view.findViewById(R.id.tv_title);
                        titleTv.setTextColor(getResources().getColor(R.color.colorTextGray));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               // mViewPager.setCurrentItem(state);
            }



        });

        mViewPager.setCurrentItem(0);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setPrivateUnread(int unread) {
       TabLayout.Tab tab = tabList.get(0);
        View view = tab.getCustomView();
        TextView unreadTv = view.findViewById(R.id.tv_unread);
        unreadTv.setText(unread + "");
        if (unread == 0){
            unreadTv.setVisibility(View.GONE);
        }else {
            unreadTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setGroupUnread(int unread) {
        TabLayout.Tab tab = tabList.get(1);
        View view = tab.getCustomView();
        TextView unreadTv = view.findViewById(R.id.tv_unread);
        unreadTv.setText(unread + "");
        if (unread == 0){
            unreadTv.setVisibility(View.GONE);
        }else {
            unreadTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setExamineApproveTabUnread(int unread) {
        TabLayout.Tab tab = tabList.get(2);
        View view = tab.getCustomView();
        TextView unreadTv = view.findViewById(R.id.tv_unread);
        unreadTv.setText(unread + "");
        if (unread == 0){
            unreadTv.setVisibility(View.GONE);
        }else {
            unreadTv.setVisibility(View.VISIBLE);
        }
    }
}
