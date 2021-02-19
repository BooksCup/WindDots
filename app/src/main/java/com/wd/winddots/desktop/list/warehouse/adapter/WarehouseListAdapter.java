package com.wd.winddots.desktop.list.warehouse.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: WarehouseListAdapter
 * Author: 郑
 * Date: 2020/7/31 2:53 PM
 * Description:
 */
public class WarehouseListAdapter extends BaseQuickAdapter<WarehouseListBean.WarehouseListItem, BaseViewHolder> {

    private Context mContext;

    public WarehouseListAdapter(int layoutResId, @Nullable List<WarehouseListBean.WarehouseListItem> data,Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WarehouseListBean.WarehouseListItem item) {

        String title2 = mContext.getString(R.string.warehouse_contact) + ": " + Utils.nullOrEmpty(item.getContactName())
                + " " + Utils.nullOrEmpty(item.getContactPhone());
        String title3 = mContext.getString(R.string.warehouse_address) + ": " + Utils.nullOrEmpty(item.getArea())
                + " " + Utils.nullOrEmpty(item.getAddress());
        helper.setText(R.id.tv_1,item.getName()).
                setText(R.id.tv_2,title2).
        setText(R.id.tv_3,title3);

    }
}
