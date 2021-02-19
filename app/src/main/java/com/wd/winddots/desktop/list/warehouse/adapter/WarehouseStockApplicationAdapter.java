package com.wd.winddots.desktop.list.warehouse.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseStockApplicationBean;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: WarehouseStockApplicationAdapter
 * Author: 郑
 * Date: 2020/8/7 10:12 AM
 * Description: 仓库库存
 */
public class WarehouseStockApplicationAdapter extends BaseQuickAdapter<WarehouseStockApplicationBean.WarehouseStockApplicationItem, BaseViewHolder> {

    private  Context mContext;

    public WarehouseStockApplicationAdapter(int layoutResId, @Nullable List<WarehouseStockApplicationBean.WarehouseStockApplicationItem> data,Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WarehouseStockApplicationBean.WarehouseStockApplicationItem item) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        helper.setText(R.id.tv_type, Utils.nullOrEmpty(item.getCreateTime()) +" "+ item.getTypeName()).
                setText(R.id.tv_goods_name, mContext.getString(R.string.warehouse_goods_name) + Utils.nullOrEmpty(item.getCreateTime())).
                setText(R.id.tv_number, mContext.getString(R.string.warehouse_number) +nf.format(item.getCount()) + Utils.nullOrEmpty(item.getGoodsUnit())).
                setText(R.id.tv_price, mContext.getString(R.string.warehouse_price) +Utils.nullOrEmpty(item.getPrice())).
                setText(R.id.tv_receiver_name, mContext.getString(R.string.warehouse_receiver_name) +Utils.nullOrEmpty(item.getReceiverName())).
                setText(R.id.tv_receiver_address, mContext.getString(R.string.warehouse_receiver_address) +Utils.nullOrEmpty(item.getReceiveAddress())).
                setText(R.id.tv_remark, mContext.getString(R.string.warehouse_remark) +Utils.nullOrEmpty(item.getRemark()));
    }
}
