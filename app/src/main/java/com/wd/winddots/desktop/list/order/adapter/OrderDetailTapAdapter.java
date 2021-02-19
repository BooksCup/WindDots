package com.wd.winddots.desktop.list.order.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.list.goods.fragment.GoodsAttrFragment;
import com.wd.winddots.desktop.list.goods.fragment.GoodsRemarkFragment;
import com.wd.winddots.desktop.list.order.fragment.OrderNumberFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: OrderDetailTapAdapter
 * Author: 郑
 * Date: 2020/8/10 10:46 AM
 * Description:
 */
public class OrderDetailTapAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"订单数量", "价格条款", "物流记录"};

    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    private GoodsAttrFragment tap1;
    private GoodsRemarkFragment tap4;

    private String mOrderId;

    private String mRemark;


    public OrderDetailTapAdapter(FragmentManager fm, String orderId) {
        super(fm);
        mOrderId = orderId;
    }


    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = OrderNumberFragment.newInstance(mOrderId);
            } else if (position == 1){
                fragment = OrderNumberFragment.newInstance(mOrderId);
            } else if (position == 2){
                fragment = OrderNumberFragment.newInstance(mOrderId);
            } else {
                fragment = GoodsRemarkFragment.newInstance(mRemark);
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
