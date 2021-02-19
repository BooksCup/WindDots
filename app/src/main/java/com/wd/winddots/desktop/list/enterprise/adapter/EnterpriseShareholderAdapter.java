package com.wd.winddots.desktop.list.enterprise.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseShareholderItem;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: EnterpriseShareholderAdapter
 * Author: 郑
 * Date: 2020/12/23 10:35 AM
 * Description: 企业股东适配器
 */
public class EnterpriseShareholderAdapter extends BaseQuickAdapter<EnterpriseDetailBean.CompanyHolderItem, BaseViewHolder> {
    public EnterpriseShareholderAdapter(int layoutResId, @Nullable List<EnterpriseDetailBean.CompanyHolderItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseDetailBean.CompanyHolderItem item) {

        EnterpriseDetailBean.CapitalItem capitalItem = item.getCapital().get(0);
        String amomon = "认缴出资额:";
        String percent = "持股比例:";
        String time = "认缴出资日期:";
        if (capitalItem != null) {
            amomon = amomon + Utils.numberNullOrEmpty(capitalItem.getAmomon());
            percent = percent + Utils.numberNullOrEmpty(capitalItem.getPercent());
            time = time + Utils.nullOrEmpty(capitalItem.getTime());
        }
//        EnterpriseDetailBean.CapitalItem capitalItemActl = item.getCapitalActl().get(0);
//
//        if (capitalItemActl != null) {
//
//        }

        String type = "股东类型:";
        switch (item.getType()) {
            case 1:
                type = type + "公司";
                break;
            case 2:
                type = type + "自然人";
                break;
            default:
                type = type + "其他";
        }
        helper.setText(R.id.tv_name, Utils.nullOrEmpty(item.getName())).
                setText(R.id.tv_percent, percent).
                setText(R.id.tv_amomon, amomon).
                setText(R.id.tv_time, time).
                setText(R.id.tv_holdertype, type);
    }
}
