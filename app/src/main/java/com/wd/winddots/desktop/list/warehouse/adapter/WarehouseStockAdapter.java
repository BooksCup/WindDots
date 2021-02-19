package com.wd.winddots.desktop.list.warehouse.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.warehouse.bean.WarehouseStockBean;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: WarehouseStockAdapter
 * Author: 郑
 * Date: 2020/8/5 10:09 AM
 * Description: 仓库库存
 */
public class WarehouseStockAdapter extends BaseQuickAdapter<WarehouseStockBean.WarehouseStockItem, BaseViewHolder> {

    private Context mContext;

    public WarehouseStockAdapter(int layoutResId, @Nullable List<WarehouseStockBean.WarehouseStockItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WarehouseStockBean.WarehouseStockItem item) {

        ImageView icon = helper.getView(R.id.icon);
        if (!StringUtils.isNullOrEmpty(item.getPhoto())){
            GlideApp.with(mContext).load(item.getPhoto()).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String residualNumber = item.getResidualNumber();
        if (StringUtils.isNullOrEmpty(residualNumber)){
            residualNumber = "0";
        }
        Float floatR = Float.valueOf(residualNumber);

        String title1 = Utils.nullOrEmpty(item.getGoodsNo()) + " (" + Utils.nullOrEmpty(item.getGoodsName()) + ") ";
        helper.setText(R.id.tv_1,title1).
        setText(R.id.tv_2,"物品类型: " + Utils.nullOrEmpty(item.getGoodsTypeName())).
        setText(R.id.tv_3,"库存数量: " + Utils.numberNullOrEmpty(nf.format(floatR))).
        setText(R.id.tv_4,"金额: " + Utils.nullOrEmpty(item.getTotalPrice()));
    }
}
