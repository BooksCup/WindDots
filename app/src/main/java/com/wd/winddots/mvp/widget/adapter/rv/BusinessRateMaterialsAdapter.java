package com.wd.winddots.mvp.widget.adapter.rv;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: BusinessRateMaterialsAdapter
 * Author: 郑
 * Date: 2020/8/25 9:54 AM
 * Description: 商圈列表汇率和原材料 item
 */
public class BusinessRateMaterialsAdapter extends BaseQuickAdapter<BusinessBean.BusinessSelectItem, BaseViewHolder> {
    public BusinessRateMaterialsAdapter(int layoutResId, @Nullable List<BusinessBean.BusinessSelectItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessBean.BusinessSelectItem item) {
//item_approvalecess_time_header_time_tv

        String text;
        if (!StringUtils.isNullOrEmpty(item.getRateId())){
            String rateCurrencyBuy = Utils.numberNullOrEmpty(item.getRateCashBuy());
            Float rateCurrencyBuyF = Float.parseFloat(rateCurrencyBuy);
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            String amountS = String.valueOf(nf.format(rateCurrencyBuyF/100));
            text = Utils.nullOrEmpty(item.getRateCurrencyName()) +":   " + amountS;
        }else {
            text = Utils.nullOrEmpty(item.getPriceName()) + ":   " + Utils.numberNullOrEmpty(item.getPriceLastTrade()) + Utils.nullOrEmpty(item.getPriceUnit());
        }
        helper.setText(R.id.item_approvalecess_time_header_time_tv,text);

    }
}
