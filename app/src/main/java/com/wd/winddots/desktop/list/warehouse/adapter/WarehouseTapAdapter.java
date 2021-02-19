package com.wd.winddots.desktop.list.warehouse.adapter;

import android.view.ViewGroup;

import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.list.warehouse.fragment.SubWarehouseFragment;
import com.wd.winddots.desktop.list.warehouse.fragment.WarehouseStockAllplcationFragment;
import com.wd.winddots.desktop.list.warehouse.fragment.WarehouseStockFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * FileName: WarehouseTapAdapter
 * Author: 郑
 * Date: 2020/7/31 4:34 PM
 * Description:
 */
public class WarehouseTapAdapter extends FragmentPagerAdapter {

    private String[] homeTitles = new String[]{"二级仓库", "库存", "物流"};
    private BaseFragment[] mBaseFragments = new BaseFragment[homeTitles.length];

    private String mWarehouseId;

    public WarehouseTapAdapter(FragmentManager fm,String warehouseId) {
        super(fm);
        mWarehouseId = warehouseId;
    }

    @Override
    public Fragment getItem(int position) {
        if (mBaseFragments[position] == null){
            BaseFragment fragment = null;
            if (position == 0){
                fragment = SubWarehouseFragment.newInstance(mWarehouseId);
            } else if (position == 1){
                fragment = WarehouseStockFragment.newInstance(mWarehouseId);
            } else{
                fragment = WarehouseStockAllplcationFragment.newInstance(mWarehouseId);
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
