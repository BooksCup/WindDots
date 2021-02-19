package com.wd.winddots.desktop.list.goods.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.list.goods.fragment.GoodsAttrFragment;
import com.wd.winddots.desktop.list.goods.fragment.GoodsRemarkFragment;
import com.wd.winddots.desktop.list.goods.fragment.GoodsStockDetailFragment;
import com.wd.winddots.desktop.list.goods.fragment.QuoteRecordFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: GoodsDetailTapAdapter
 * Author: 郑
 * Date: 2020/7/28 11:14 AM
 * Description:
 */
public class GoodsDetailTapAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"物品属性", "报价记录", "库存记录","备注"};

    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    private GoodsAttrFragment tap1;
    private GoodsRemarkFragment tap4;

    private String mGoodsId;

    private String mRemark;


    public GoodsDetailTapAdapter(FragmentManager fm,String goodsId) {
        super(fm);
        mGoodsId = goodsId;
    }


    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = GoodsAttrFragment.newInstance();
                tap1 = (GoodsAttrFragment)fragment;
            } else if (position == 1){
                fragment = QuoteRecordFragment.newInstance(mGoodsId);
            } else if (position == 2){
                fragment = GoodsStockDetailFragment.newInstance(mGoodsId);
            } else {
                fragment = GoodsRemarkFragment.newInstance(mRemark);
                tap4 = (GoodsRemarkFragment) fragment;
            }
            mBaseFragments[position] = fragment;
        }
        return mBaseFragments[position];
    }

    public void setRemark(String remark){
        mRemark = remark;
        if (tap4 != null){
            tap4.setRemark(remark);
        }
    }

    public void setData(List<String> attrList, List<String> speList){
        if (tap1 != null){
            tap1.setData(attrList,speList);
        }
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
