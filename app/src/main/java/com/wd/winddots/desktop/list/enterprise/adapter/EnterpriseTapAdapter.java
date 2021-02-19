package com.wd.winddots.desktop.list.enterprise.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.desktop.list.enterprise.fragment.EnterpriseBaseInfoFragment;
import com.wd.winddots.desktop.list.enterprise.fragment.EnterpriseChangeRecordFragment;
import com.wd.winddots.desktop.list.enterprise.fragment.EnterpriseLitigationFragment;
import com.wd.winddots.desktop.list.enterprise.fragment.EnterpriseShareholderFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: EnterpriseTapAdapter
 * Author: 郑
 * Date: 2020/12/23 9:52 AM
 * Description:
 */
public class EnterpriseTapAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"基本信息", "企业股东", "变更记录", "法律诉讼"};
    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];


    EnterpriseDetailBean mEnterpriseDetailBean;

    EnterpriseBaseInfoFragment baseInfoFragment;
    EnterpriseShareholderFragment shareholderFragment;
    EnterpriseChangeRecordFragment changeRecordFragment;
    EnterpriseLitigationFragment litigationFragment;
    public EnterpriseTapAdapter(FragmentManager fm) {
        super(fm);
    }




    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null) {
            BaseFragment fragment = null;
            if (position == 0) {
                fragment = EnterpriseBaseInfoFragment.newInstance();
                baseInfoFragment = (EnterpriseBaseInfoFragment)fragment;
            } else if (position == 1) {
                fragment = EnterpriseShareholderFragment.newInstance();
                shareholderFragment = (EnterpriseShareholderFragment)fragment;
            } else if (position == 2) {
                fragment = EnterpriseChangeRecordFragment.newInstance();
                changeRecordFragment = (EnterpriseChangeRecordFragment)fragment;
                if (mEnterpriseDetailBean != null){
                    changeRecordFragment.setData(mEnterpriseDetailBean);
                }
            } else {
                fragment = EnterpriseLitigationFragment.newInstance();
                litigationFragment = (EnterpriseLitigationFragment)fragment;
                if (mEnterpriseDetailBean != null){
                    litigationFragment.setData(mEnterpriseDetailBean);
                }
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

    public void setData(EnterpriseDetailBean enterpriseDetailBean) {
        mEnterpriseDetailBean = enterpriseDetailBean;
        baseInfoFragment.setData(enterpriseDetailBean);
        shareholderFragment.setData(enterpriseDetailBean);
        changeRecordFragment.setData(enterpriseDetailBean);
        litigationFragment.setData(enterpriseDetailBean);
    }
}
